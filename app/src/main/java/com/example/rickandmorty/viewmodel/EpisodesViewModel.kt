package com.example.rickandmorty.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.rickandmorty.data.network.EpisodeResponse
import com.example.rickandmorty.repository.EpisodeRepository
import io.reactivex.Single

class EpisodesViewModel(application: Application) : AndroidViewModel(application) {

    private val episodeRepository = EpisodeRepository(application.baseContext)

    fun getEpisodes(characterId: String) : List<EpisodeResponse> {
        return episodeRepository.getEpisodes(characterId)
    }

    fun insertEpisodes(episodes: EpisodeResponse) {
        return episodeRepository.insertEpisodes(episodes)
    }

    fun fetchEpisodes(episodeUrl: String): Single<EpisodeResponse> {
        return episodeRepository.fetchCharacterEpisodes(episodeUrl)
    }
}