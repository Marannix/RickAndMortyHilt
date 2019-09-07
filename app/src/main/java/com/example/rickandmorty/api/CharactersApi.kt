package com.example.rickandmorty.api

import com.example.rickandmorty.data.network.CharactersResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersApi {

    @GET("character/")
    fun getCharacters(@Query("page") page: Int): Single<CharactersResponse>

    @GET(" ")
    fun getNextCharacters(): Single<CharactersResponse>
}