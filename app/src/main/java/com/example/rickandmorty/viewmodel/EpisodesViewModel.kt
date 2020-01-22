package com.example.rickandmorty.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.characters.CharacterLocation
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.data.favourites.FavouriteModel
import com.example.rickandmorty.data.network.EpisodeResponse
import com.example.rickandmorty.repository.EpisodeRepository
import com.example.rickandmorty.repository.FavouriteRepository
import com.example.rickandmorty.usecase.EpisodeUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class EpisodesViewModel @Inject constructor(
    private val episodeUseCase: EpisodeUseCase,
    private val episodeRepository: EpisodeRepository,
    private val favouriteRepository: FavouriteRepository
) : ViewModel() {

//    fun getEpisodes(characterId: String): List<EpisodeResponse> {
//        return episodeRepository.getEpisodesFromDb(characterId)
//    }

    private val disposables = CompositeDisposable()
    val viewState = MutableLiveData<ViewState>()

//    fun insertEpisodes(episodes: EpisodeResponse) {
//        return episodeRepository.storeEpisodesInDb(episodes)
//    }

//    fun stuff(characterId: String, episodeUrl: List<String>) {
//        val disposable = episodeRepository.getEpisodes(characterId, episodeUrl)
//            .observeOn(AndroidSchedulers.mainThread()).subscribe({
//                Log.d("yes", it.toString())
//            }, {
//                Log.d("empty", it.message)
//            })
//
//        disposables.add(disposable)
//    }

//    fun start(character: CharactersResults) {
//        //store selected character in shared preference?
//        disposables.add(getEpisodes(character))
//    }

     fun getEpisodes(character: CharactersResults) {
        val disposable = episodeUseCase.getEpisodesDataState(character)
            .observeOn(AndroidSchedulers.mainThread())
            .map { episodeDataState ->
                return@map when (episodeDataState) {
                    is EpisodeUseCase.EpisodeDataState.Success -> {
                        ViewState.Content(episodeDataState.listOfEpisodes)
                    }

                    is EpisodeUseCase.EpisodeDataState.Error -> {
                        ViewState.Error(episodeDataState.message)
                    }
                }
            }
            .doOnSubscribe { viewState.value = ViewState.Loading }
            .subscribe { viewState ->
                this.viewState.value = viewState
            }
        disposables.add(disposable)
    }

//    fun fetchEpisodes(episodeUrl: String): Single<EpisodeResponse> {
//        return episodeRepository.fetchCharacterEpisodes(episodeUrl)
//    }

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
        data class Content(val listOfEpisodes: List<EpisodeResponse>) : ViewState()
        data class Error(val message: String?) : ViewState()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}