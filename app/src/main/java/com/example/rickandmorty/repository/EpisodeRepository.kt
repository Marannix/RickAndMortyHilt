package com.example.rickandmorty.repository

import com.example.rickandmorty.api.EpisodeApi
import com.example.rickandmorty.data.episodes.EpisodesDao
import com.example.rickandmorty.data.network.EpisodeResponse
import io.reactivex.Single
import javax.inject.Inject

class EpisodeRepository @Inject constructor(
    private val episodesDao: EpisodesDao,
    private val episodeApi: EpisodeApi
) {

    fun fetchCharacterEpisodes(episodeUrl: String): Single<EpisodeResponse> {
        return episodeApi.getCharacterEpisodes(episodeUrl)
    }

    fun storeEpisodesInDb(episodes: EpisodeResponse) {
        episodesDao.insertEpisodes(episodes)
    }

    fun getEpisodesFromDb(characterId: String): List<EpisodeResponse> {
        return episodesDao.getEpisodes(characterId)
    }

}