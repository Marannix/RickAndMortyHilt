package com.example.rickandmorty.api

import com.example.rickandmorty.data.network.CharactersResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface CharactersApi {

    // Maybe instead of passing page just getvalue character/?page=1
    @GET("character/")
    fun getCharacters(@Query("page") page: Int): Single<CharactersResponse>

    @GET
    fun getNextCharacters(@Url nextCharactersUrl: String): Single<CharactersResponse>

    @GET
    fun getPreviousCharacters(@Url previousCharactersUrl: String): Single<CharactersResponse>
}