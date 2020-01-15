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
    // TODO: Create fake profile and use either id 48, 165 or 200 information

//    fun getCharacters(): Observable<List<CharactersResults>> {
//        // Added concatArrayEagerDelayError to repository when fetching data from api and database,
//        //  it delays any errors (no network from api) until both api and database source terminate
//        return Observable.concatArrayDelayError(
//            getCharactersFromApi().toObservable(),
//            getCharactersFromDb()
//        )
//    }

    fun getCharacters(): Observable<List<CharactersResults>> {
        return getCharactersFromDb()
    }


    private fun fetchNextCharacters(nextUrl: String): Single<CharactersResponse> {
        return charactersApi.getNextCharacters(nextUrl)
            .doOnSuccess { response ->
                if (!response.charactersResults.isNullOrEmpty()) {
                    storeCharactersInDb(response.charactersResults)
                }

            }
            .flatMap { response ->
                //                if (response.characterPageInfo.next.isNotEmpty()) {
                fetchNextCharacters(response.characterPageInfo.next)
                //    }
            }
            .doOnError {
                // TODO: I wonder if this is useful, might not be
                Observable.empty<CharactersResults>()
            }
            .subscribeOn(Schedulers.io())
    }

    private fun getCharactersFromApi(): Single<List<CharactersResults>> {
        return charactersApi.getCharacters().doOnSuccess { response ->
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
                    getCharactersFromApi().toObservable()
                } else {
                    Observable.just(list)
                }
            }
    }

    fun removeCharactersFromDb() {
        charactersDao.deleteCharacters()
    }

}