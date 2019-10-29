package com.example.rickandmorty.api

import com.example.rickandmorty.data.network.EpisodeResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface EpisodeApi {

    @GET
    fun getCharacterEpisodes(@Url episodeUrl: String): Single<EpisodeResponse>
}