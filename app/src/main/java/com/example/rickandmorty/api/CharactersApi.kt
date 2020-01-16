package com.example.rickandmorty.api

import com.example.rickandmorty.data.network.CharactersResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface CharactersApi {

    @GET("character/?page=1")
    fun getCharacters(): Single<CharactersResponse>

    @GET
    fun getNextCharacters(@Url nextCharactersUrl: String): Observable<CharactersResponse>

    @GET
    fun getPreviousCharacters(@Url previousCharactersUrl: String): Single<CharactersResponse>
}