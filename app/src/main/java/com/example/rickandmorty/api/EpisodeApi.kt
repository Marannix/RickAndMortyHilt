package com.example.rickandmorty.api

import com.example.rickandmorty.data.network.EpisodeResponse
import io.reactivex.Single
import retrofit2.http.GET

interface EpisodeApi {

    @GET(" ")
    fun getCharacterEpisodes(): Single<EpisodeResponse>
}