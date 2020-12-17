package com.example.rickandmorty.repository

import com.example.rickandmorty.api.CharactersApi
import com.example.rickandmorty.data.characters.CharactersDao
import com.example.rickandmorty.data.characters.CharactersResults
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject



class CharactersRepository @Inject constructor(
    private val charactersDao: CharactersDao,
    private val charactersApi: CharactersApi
) {

    /**
     * Just save into Database
     */
    fun getCharactersFromApiRx(): Completable {
        return charactersApi.getCharacters()
            .subscribeOn(Schedulers.io())
            .concatMap {  response ->
                if (response.characterPageInfo.next.isNullOrEmpty()) {
                    Observable.just(response.charactersResults)
                } else {
                    Observable.just(response.charactersResults)
                        .concatWith(fetchNextCharacters(response.characterPageInfo.next))
                }
            }
            .doOnNext {
                storeCharactersInDb(it)
            }.ignoreElements()
    }

    fun getCharactersFromDbRx(): Observable<List<CharactersResults>>  {
        return charactersDao.getCharacters()
    }

    fun getCharacters(): Observable<List<CharactersResults>> {
        return getCharactersFromDb()
    }

    private fun getCharactersFromApi(): Observable<CharactersResults> {
        return fetchInitialCharacters()
            .concatMap { listOfCharacters -> Observable.fromIterable(listOfCharacters) }
    }

    private fun fetchInitialCharacters() : Observable<List<CharactersResults>> {
        return charactersApi.getCharacters()
            .subscribeOn(Schedulers.io())
            .concatMap {  response ->
                if (response.characterPageInfo.next.isNullOrEmpty()) {
                    Observable.just(response.charactersResults)
                } else {
                    Observable.just(response.charactersResults)
                        .concatWith(fetchNextCharacters(response.characterPageInfo.next))
                }
            }
            .doOnNext {
                storeCharactersInDb(it)
            }
    }

    private fun fetchNextCharacters(nextUrl: String): Observable<List<CharactersResults>> {
        return charactersApi.getNextCharacters(nextUrl)
            .subscribeOn(Schedulers.io())
            .concatMap { response ->
                if (response.characterPageInfo.next.isNullOrEmpty()) {
                    Observable.just(response.charactersResults)
                } else {
                    Observable.just(response.charactersResults)
                        .concatWith(fetchNextCharacters(response.characterPageInfo.next))
                }
            }
            .doOnNext {
                storeCharactersInDb(it)
            }
    }

    private fun storeCharactersInDb(characters: List<CharactersResults>) {
        charactersDao.insertCharacters(characters)
    }

    private fun getCharactersFromDb(): Observable<List<CharactersResults>> {
        return charactersDao.getCharacters()
            .flatMap { list ->
                return@flatMap if (list.isEmpty()) {
                    getCharactersFromApi().toList().toObservable()
                } else {
                    Observable.just(list)
                }
            }
    }
}