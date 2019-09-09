package com.example.rickandmorty.repository

import com.example.rickandmorty.data.network.ApiService
import com.example.rickandmorty.data.network.EpisodeResponse
import io.reactivex.Single

class EpisodeRepository {

    private val apiService = object : ApiService {}

    fun fetchCharacterEpisodes(episodeUrl: String) : Single<EpisodeResponse> {
        return apiService.loadCharacterEpisode(episodeUrl).getCharacterEpisodes()
    }
}