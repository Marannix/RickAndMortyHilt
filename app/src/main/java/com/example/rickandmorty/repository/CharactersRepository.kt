package com.example.rickandmorty.repository

import com.example.rickandmorty.api.CharactersApi
import com.example.rickandmorty.data.characters.CharactersDao
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.data.network.CharactersResponse
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val charactersDao: CharactersDao,
    private val charactersApi: CharactersApi
) {

    // TODO: Add and remove favourite characters and save to database

    fun getCharacters(): Observable<List<CharactersResults>> {
        // Added concatArrayEagerDelayError to repository when fetching data from api and database,
        //  it delays any errors (no network from api) until both api and database source terminate
        return Observable.concatArrayEagerDelayError(
            getCharactersFromApi(1).toObservable(),
            getCharactersFromDb()
        )
    }

    private fun fetchNextCharacters(nextUrl: String): Single<CharactersResponse> {
        return charactersApi.getNextCharacters(nextUrl)
            .doOnSuccess { response ->
                storeCharactersInDb(response.charactersResults)
            }
            .doOnError {
                // TODO: I wonder if this is useful, might not be
                Observable.empty<CharactersResults>()
            }
            .subscribeOn(Schedulers.io())
    }

    private fun getCharactersFromApi(page: Int): Single<List<CharactersResults>> {
        return charactersApi.getCharacters(page).doOnSuccess { response ->
            storeCharactersInDb(response.charactersResults)
        }
            .flatMap {
                //TODO: Need to check if next page exists, might want to do nothing when last character is reached
                fetchNextCharacters(it.characterPageInfo.next)
            }
            .map(CharactersResponse::charactersResults)
            .subscribeOn(Schedulers.io())
    }

    private fun storeCharactersInDb(characters: List<CharactersResults>) {
        charactersDao.insertCharacters(characters)
    }

    private fun getCharactersFromDb(): Observable<List<CharactersResults>> {
        return charactersDao.getCharacters()
            .toObservable()
            .flatMap { list ->
                return@flatMap if (list.isEmpty()) {
                    Observable.empty()
                } else {
                    Observable.just(list)
                }
            }
    }

}