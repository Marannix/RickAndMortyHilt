package com.example.rickandmorty.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.rickandmorty.data.characters.CharacterEpisodeResponse
import com.example.rickandmorty.data.characters.CharacterLocation
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.data.episodes.EpisodesResult
import com.example.rickandmorty.data.favourites.FavouriteModel
import com.example.rickandmorty.repository.FavouriteRepository
import com.example.rickandmorty.usecase.EpisodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

//todo rename to character detail
@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val episodeUseCase: EpisodeUseCase,
    private val favouriteRepository: FavouriteRepository
) : BaseViewModel() {

    val viewState = MutableLiveData<ViewState>()
    val episodeViewState = MutableLiveData<EpisodeViewState>()

    fun getEpisodes(character: CharactersResults) {
        episodeUseCase.getEpisodesDataState(character)
            .observeOn(AndroidSchedulers.mainThread())
            .map { episodeDataState ->
                return@map when (episodeDataState) {
                    is EpisodeUseCase.CharacterEpisodesDataState.Success -> {
                        ViewState.Content(episodeDataState.listOfCharacterEpisodes)
                    }

                    is EpisodeUseCase.CharacterEpisodesDataState.Error -> {
                        ViewState.Error(episodeDataState.message)
                    }
                }
            }
            .doOnSubscribe { viewState.value = ViewState.Loading }
            .subscribe { viewState ->
                this.viewState.value = viewState
            }.addDisposable()
    }

    fun getAllEpisodes() {
        episodeUseCase.getAllEpisodeDataState()
            .observeOn(AndroidSchedulers.mainThread())
            .map { dataState ->
                return@map when (dataState) {
                    is EpisodeUseCase.EpisodesDataState.Success -> {
                        EpisodeViewState.Content(dataState.listOfEpisodes)
                    }
                    is EpisodeUseCase.EpisodesDataState.Error -> {
                        EpisodeViewState.Error(dataState.message)
                    }
                }
            }
            .doOnSubscribe { episodeViewState.value = EpisodeViewState.Loading }
            .subscribe { episodeViewState ->
                this.episodeViewState.value = episodeViewState
            }.addDisposable()
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

//    fun getFavourite() {
//        val disposable = favouriteRepository.getFavourite().observeOn(AndroidSchedulers.mainThread()).subscribe {
//            if (it.isEmpty()) {
//                Log.d("empty", "empty")
//            } else {
//                Log.d("something", it.toString())
//            }
//        }
//    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Content(val listOfCharacterEpisodes: List<CharacterEpisodeResponse>) :
            ViewState()

        data class Error(val message: String?) : ViewState()
    }

    sealed class EpisodeViewState {
        object Loading : EpisodeViewState()
        data class Content(val listOfEpisodes: List<EpisodesResult>) : EpisodeViewState()
        data class Error(val message: String?) : EpisodeViewState()
    }
}