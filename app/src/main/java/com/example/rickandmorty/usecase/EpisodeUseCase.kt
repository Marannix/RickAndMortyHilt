package com.example.rickandmorty.usecase

import com.example.rickandmorty.data.characters.CharacterEpisodeResponse
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.data.episodes.EpisodesResult
import com.example.rickandmorty.repository.EpisodeRepository
import io.reactivex.Observable
import javax.inject.Inject

class EpisodeUseCase @Inject constructor(
    private val episodeRepository: EpisodeRepository
) {

    fun getEpisodesDataState(character: CharactersResults): Observable<CharacterEpisodesDataState> {
        return episodeRepository.getEpisodes(character)
            .map<CharacterEpisodesDataState> { listOfEpisodes ->
                CharacterEpisodesDataState.Success(listOfEpisodes)
            }
            .onErrorReturn { error -> CharacterEpisodesDataState.Error(error.message) }
    }

    fun getAllEpisodeDataState() : Observable<EpisodesDataState> {
        return episodeRepository.getAllEpisodes()
            .map<EpisodesDataState> {listOfEpisodes ->
               EpisodesDataState.Success(listOfEpisodes)
            }
            .onErrorReturn { error -> EpisodesDataState.Error(error.message) }
    }

    sealed class CharacterEpisodesDataState {
        data class Success(val listOfCharacterEpisodes: List<CharacterEpisodeResponse>) : CharacterEpisodesDataState()
        data class Error(val message: String?) : CharacterEpisodesDataState()
    }

    sealed class EpisodesDataState {
        data class Success(val listOfEpisodes: List<EpisodesResult>) : EpisodesDataState()
        data class Error(val message: String?) : EpisodesDataState()
    }
}