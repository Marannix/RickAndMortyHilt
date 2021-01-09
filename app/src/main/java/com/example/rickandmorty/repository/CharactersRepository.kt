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
}