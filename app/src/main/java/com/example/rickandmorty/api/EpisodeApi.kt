package com.example.rickandmorty.api

import com.example.rickandmorty.data.characters.CharacterEpisodeResponse
import com.example.rickandmorty.data.episodes.EpisodeResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

interface EpisodeApi {

    @GET
    fun getCharacterEpisodes(@Url episodeUrl: String): Observable<CharacterEpisodeResponse>

    @GET("episode/")
    fun getAllEpisodes(): Observable<EpisodeResponse>

}