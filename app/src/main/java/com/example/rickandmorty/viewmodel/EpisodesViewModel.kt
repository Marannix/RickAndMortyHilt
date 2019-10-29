package com.example.rickandmorty.viewmodel

import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.network.EpisodeResponse
import com.example.rickandmorty.repository.EpisodeRepository
import io.reactivex.Single
import javax.inject.Inject

class EpisodesViewModel @Inject constructor(val episodeRepository: EpisodeRepository) : ViewModel() {

    fun getEpisodes(characterId: String): List<EpisodeResponse> {
        return episodeRepository.getEpisodesFromDb(characterId)
    }

    fun insertEpisodes(episodes: EpisodeResponse) {
        return episodeRepository.storeEpisodesInDb(episodes)
    }

    fun fetchEpisodes(episodeUrl: String): Single<EpisodeResponse> {
        return episodeRepository.fetchCharacterEpisodes(episodeUrl)
    }
}