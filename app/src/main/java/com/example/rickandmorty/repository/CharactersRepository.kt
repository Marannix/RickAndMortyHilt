package com.example.rickandmorty.repository

import com.example.rickandmorty.api.CharactersApi
import com.example.rickandmorty.data.characters.CharactersDao
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.data.network.CharactersResponse
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val charactersDao: CharactersDao,
    private val charactersApi: CharactersApi
) {

    // TODO: Add and remove favourite characters and save to database

//    fun getCharacters(): Observable<List<CharactersResults>> {
//        // Added concatArrayEagerDelayError to repository when fetching data from api and database,
//        //  it delays any errors (no network from api) until both api and database source terminate
//        return Observable.concatArrayEagerDelayError(
//            getCharactersFromApi(1).toObservable(),
//            getCharactersFromDb()
//        )
//    }

    fun getCharacters(): Observable<List<CharactersResults>> {
        return getCharactersFromDb()
    }

    private fun fetchNextCharacters(nextUrl: String): Observable<CharactersResponse> {
        return charactersApi.getNextCharacters(nextUrl)
            .subscribeOn(Schedulers.io())
            .concatMap { response ->
                if (response.characterPageInfo.next.isEmpty()) {
                    Observable.just(response)
                }
                Observable.just(response)
                    .concatWith(fetchNextCharacters(response.characterPageInfo.next))

            }
            .doOnNext {
                storeCharactersInDb(it.charactersResults)
            }


//            .reduce(
//                List.<ResponseObject>builder(),
//                (builder, response) -> builder.addAll(response.results))
//        // Convert list builder to one List<ResponseObject> of all the things.
//        .map(builder -> builder.build())
////            .toSortedList()
//            .toObservable()


    }


//    private fun getCharactersFromApi() Observable<CharactersResponse> {
//        return fetchNextCharacters("hi")
//            .doonsu
//
//    }

//    private fun fetchNextCharacters(nextUrl: String): Single<CharactersResponse> {
//        return charactersApi.getNextCharacters(nextUrl)
//            .doOnSuccess { response ->
//                if (!response.charactersResults.isNullOrEmpty()) {
//                    storeCharactersInDb(response.charactersResults)
//                }
//            }
//            .toObservable()
//            .doAfterNext {
//                if (it.characterPageInfo.next.isNotEmpty()) {
//                    fetchNextCharacters(it.characterPageInfo.next)
//                }
//            }
//            //     .flatMap { response ->
////                fetchNextCharacters(response.characterPageInfo.next)
//            //}
//            .doOnError {
//                // TODO: I wonder if this is useful, might not be
//                Observable.empty<CharactersResults>()
//            }
//            .subscribeOn(Schedulers.io()).singleOrError()
//    }
//
//    private fun getCharactersFromApi(): Single<List<CharactersResults>> {
//        return charactersApi.getCharacters().doOnSuccess { response ->
//            storeCharactersInDb(response.charactersResults)
//            getPageAndNext(response.characterPageInfo.next)
//        }
//            .map(CharactersResponse::charactersResults)
//            .subscribeOn(Schedulers.io())
////    }
//
//    private fun getCharactersFromApi(url: String): Observable<List<CharactersResults>> {
//        return charactersApi.getNextCharacters(url)
//            .subscribeOn(Schedulers.io())
//            .concatMap (object: )
//                if (it.characterPageInfo.next.isEmpty()) {
//                    Observable.just(it)
//                } else {
//                    Observable.just(it)
//                        .concatWith(getCharactersFromApi(it.characterPageInfo.next))
//
//                }
//            }.map(CharactersResponse::charactersResults)

    // }

//    fun getPageAndNext(page: Int): Observable<ApiResponse> {
//        return getResults(page)
//            .concatMap(object : Func1<ApiResponse, Observable<ApiResponse>>() {
//
//                fun call(response: ApiResponse): Observable<ApiResponse> {
//                    // Terminal case.
//                    return if (response.next == null) {
//                        Observable.just<ApiResponse>(response)
//                    } else Observable.just<ApiResponse>(response)
//                        .concatWith(getPageAndNext(response.next))
//                }
//
//            })
//    }

    private fun getCharactersFromApi(): Observable<List<CharactersResults>> {
        return fetchNextCharacters("https://rickandmortyapi.com/api/character/?page=1")
            .map(CharactersResponse::charactersResults)
    }
    private fun storeCharactersInDb(characters: List<CharactersResults>) {
        charactersDao.insertCharacters(characters)
    }

    private fun getCharactersFromDb(): Observable<List<CharactersResults>> {
        return charactersDao.getCharacters()
            .toObservable()
            .flatMap { list ->
                return@flatMap if (list.isEmpty()) {
                    getCharactersFromApi()
                } else {
                    Observable.just(list)
                }
            }
    }
}