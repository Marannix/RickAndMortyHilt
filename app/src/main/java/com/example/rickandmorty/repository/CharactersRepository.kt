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


    fun getCharacters(): Observable<List<CharactersResults>> {
        return Observable.concatArrayEagerDelayError(
            getCharactersFromApi(1).toObservable(),
            getCharactersFromDb()
        )
    }

    private fun fetchNextCharacters(nextUrl: String): Single<CharactersResponse> {
        return charactersApi.getNextCharacters(nextUrl)
            .doOnSuccess { response ->
                storeCharactersInDb(response.charactersResults)
                // TODO: Try to find a way to call api response several times (Roughly 25 times)
            }
            .doOnError {
                Log.d("error fetch", it.message!!)
                Observable.empty<CharactersResults>()
            }
            .subscribeOn(Schedulers.io())
    }

    private fun getCharactersFromApi(page: Int): Single<List<CharactersResults>> {
        return charactersApi.getCharacters(page).doOnSuccess { response ->
            storeCharactersInDb(response.charactersResults)
        }
            .flatMap { fetchNextCharacters(it.characterPageInfo.next) }
            .map {
                // TODO: This is probably causing my list to only display the last item retrieved
                it.charactersResults
            }
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