package com.example.rickandmorty.repository

import com.example.rickandmorty.api.CharactersApi
import com.example.rickandmorty.data.characters.CharactersDao
import com.example.rickandmorty.data.characters.CharactersResults
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject



class CharactersRepository @Inject constructor(
    private val charactersDao: CharactersDao,
    private val charactersApi: CharactersApi
) {

    fun getCharacters(): Observable<List<CharactersResults>> {
        return getCharactersFromDb()
    }

    private fun fetchNextCharacters(nextUrl: String): Observable<List<CharactersResults>> {
        return charactersApi.getNextCharacters(nextUrl)
            .subscribeOn(Schedulers.io())
            .concatMap { response ->
                if (response.characterPageInfo.next.isEmpty()) {
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

    private fun getCharactersFromApi(): Observable<CharactersResults> {
        return fetchNextCharacters("https://rickandmortyapi.com/api/character/?page=1")
            .concatMap { listOfCharacters -> Observable.fromIterable(listOfCharacters) }
    }

    private fun storeCharactersInDb(characters: List<CharactersResults>) {
        charactersDao.insertCharacters(characters)
    }

    private fun getCharactersFromDb(): Observable<List<CharactersResults>> {
        return charactersDao.getCharacters()
            .toObservable()
            .flatMap { list ->
                return@flatMap if (list.isEmpty()) {
                    getCharactersFromApi().toList().toObservable()
                } else {
                    Observable.just(list)
                }
            }
    }
}