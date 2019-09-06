package com.example.rickandmorty.api

import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersApi {

    @GET("character/")
    fun getCharacters(@Query("page") page: Int)

}