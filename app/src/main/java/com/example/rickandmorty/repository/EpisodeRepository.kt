package com.example.rickandmorty.repository

import android.content.Context
import com.example.rickandmorty.api.EpisodeApi
import com.example.rickandmorty.data.network.EpisodeResponse
import com.example.rickandmorty.database.DatabaseHelper
import io.reactivex.Single
import javax.inject.Inject

class EpisodeRepository @Inject constructor(
    private val episodeApi: EpisodeApi,
    private val context: Context
) {

    private val databaseHelper = DatabaseHelper(context)

    fun fetchCharacterEpisodes(episodeUrl: String): Single<EpisodeResponse> {
        return episodeApi.getCharacterEpisodes(episodeUrl)
    }

    fun insertEpisodes(episodes: EpisodeResponse) {
        databaseHelper.insertEpisodes(episodes)
    }

    fun getEpisodes(characterId: String): List<EpisodeResponse> {
        return databaseHelper.getEpisodes(characterId)
    }

}