package com.example.rickandmorty.repository

import android.content.Context
import com.example.rickandmorty.data.network.ApiService
import com.example.rickandmorty.data.network.EpisodeResponse
import com.example.rickandmorty.database.DatabaseHelper
import io.reactivex.Single

class EpisodeRepository(context: Context) {

    private val apiService = object : ApiService {}
    private val databaseHelper = DatabaseHelper(context)

    fun fetchCharacterEpisodes(episodeUrl: String): Single<EpisodeResponse> {
        return apiService.loadCharacterEpisode(episodeUrl).getCharacterEpisodes()
    }

    fun insertEpisodes(episodes: EpisodeResponse) {
        databaseHelper.insertEpisodes(episodes)
    }

    fun getEpisodes(characterId: String) : List<EpisodeResponse> {
        return databaseHelper.getEpisodes(characterId)
    }

}