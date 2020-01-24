package com.example.rickandmorty.usecase

import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.data.characters.CharacterEpisodeResponse
import com.example.rickandmorty.repository.EpisodeRepository
import io.reactivex.Observable
import javax.inject.Inject

class EpisodeUseCase @Inject constructor(
    private val episodeRepository: EpisodeRepository
){

    fun  getEpisodesDataState(character: CharactersResults) : Observable<EpisodeDataState> {
        return episodeRepository.getEpisodes(character)
            .map<EpisodeDataState> { listOfEpisodes ->
                EpisodeDataState.Success(listOfEpisodes)
            }
            .onErrorReturn { error-> EpisodeDataState.Error(error.message) }
    }

    sealed class EpisodeDataState {
        data class Success(val listOfCharacterEpisodes: List<CharacterEpisodeResponse>) : EpisodeDataState()
        data class Error(val message: String?) : EpisodeDataState()
    }
}