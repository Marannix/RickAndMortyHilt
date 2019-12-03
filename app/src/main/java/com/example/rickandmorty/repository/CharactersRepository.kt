package com.example.rickandmorty.repository

import android.util.Log
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

// TODO: Find a from characters Api solution to getPageState
//  val pageStateLiveData = MutableLiveData<CharactersPageInfo>()
//  fun getCharacterPageInfo() = pageStateLiveData

    fun getCharacters(): Observable<List<CharactersResults>> {
        return Observable.concatArray(
            getCharactersFromDb(),
            getCharactersFromApi(1).toObservable()
        )
    }

    private fun fetchNextCharacters(nextCharactersUrl: String): Single<List<CharactersResults>> {
        return charactersApi.getNextCharacters(nextCharactersUrl)
            .doOnSuccess { response ->
                storeCharactersInDb(response.charactersResults)
            }
            .map {it.charactersResults}
            .doOnError {
                Log.d("error fetch", it.message!!)
                Observable.empty<CharactersResults>()
            }
            .subscribeOn(Schedulers.io())
    }

    private fun fetchPreviousCharacters(previousCharactersUrl: String): Single<CharactersResponse> {
        return charactersApi.getPreviousCharacters(previousCharactersUrl)
            .doOnSuccess { response ->
                storeCharactersInDb(response.charactersResults)
            }
            .doOnError { Observable.empty<CharactersResults>() }
            .subscribeOn(Schedulers.io())
    }

    private fun getCharactersFromApi(page: Int): Single<List<CharactersResults>> {
        return charactersApi.getCharacters(page).doOnSuccess { response ->
            storeCharactersInDb(response.charactersResults)
        }
            .flatMap {response ->
                fetchNextCharacters(response.characterPageInfo.next)
                fetchPreviousCharacters(response.characterPageInfo.prev)
            }
            .map { it.charactersResults }
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