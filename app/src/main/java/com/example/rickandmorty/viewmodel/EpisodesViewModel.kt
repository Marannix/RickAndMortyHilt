package com.example.rickandmorty.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.characters.CharacterLocation
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.data.favourites.FavouriteModel
import com.example.rickandmorty.data.network.EpisodeResponse
import com.example.rickandmorty.repository.EpisodeRepository
import com.example.rickandmorty.repository.FavouriteRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class EpisodesViewModel @Inject constructor(
    private val episodeRepository: EpisodeRepository,
    private val favouriteRepository: FavouriteRepository
) : ViewModel() {

    fun getEpisodes(characterId: String): List<EpisodeResponse> {
        return episodeRepository.getEpisodesFromDb(characterId)
    }

    fun insertEpisodes(episodes: EpisodeResponse) {
        return episodeRepository.storeEpisodesInDb(episodes)
    }

    fun fetchEpisodes(episodeUrl: String): Single<EpisodeResponse> {
        return episodeRepository.fetchCharacterEpisodes(episodeUrl)
    }

    fun insertFavourite(character: CharactersResults) {
        favouriteRepository.storeInFavourite(
            FavouriteModel(
                character.id,
                character.name,
                character.status,
                character.species,
                character.gender,
                character.image,
                CharacterLocation(character.location.name),
                character.episode
            )
        )
    }

    fun removeFromFavourites(character: CharactersResults) {
        favouriteRepository.removeFromFavourites(
            FavouriteModel(
                character.id,
                character.name,
                character.status,
                character.species,
                character.gender,
                character.image,
                CharacterLocation(character.location.name),
                character.episode
            )
        )
    }

    fun isFavourite(characterId: String): Boolean {
        return favouriteRepository.isFavourite(characterId)
    }

    fun getFavourite() {
        val disposable = favouriteRepository.getFavourite().observeOn(AndroidSchedulers.mainThread()).subscribe {
            if (it.isEmpty()) {
                Log.d("empty", "empty")
            } else {
                Log.d("something", it.toString())
            }
        }
    }

}