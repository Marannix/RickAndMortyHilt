package com.example.rickandmorty.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.rickandmorty.data.favourites.FavouriteModel
import com.example.rickandmorty.usecase.FavouriteUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FavouriteViewModel @Inject constructor(
    private val favouriteUseCase: FavouriteUseCase
) : BaseViewModel() {

    private val viewState = MutableLiveData<FavouriteViewState>()

    fun getFavourites() {
        favouriteUseCase.getFavouriteDataState()
            .observeOn(AndroidSchedulers.mainThread())
            .map { favouriteDataState ->
                return@map when (favouriteDataState) {
                    is FavouriteUseCase.FavouriteDataState.Success -> {
                        FavouriteViewState.Content(favouriteDataState.favourites)
                    }
                    is FavouriteUseCase.FavouriteDataState.Error -> {
                        FavouriteViewState.Empty(favouriteDataState.errorMessage)
                    }
                }
            }
            .doOnSubscribe { viewState.value = FavouriteViewState.Loading }
            .subscribe { viewState ->
                this.viewState.value = viewState
            }.addDisposable()
    }

    fun getViewState(): MutableLiveData<FavouriteViewState> {
        return viewState
    }

    sealed class FavouriteViewState {
        object Loading : FavouriteViewState()
        data class Content(val listOfFavourites: List<FavouriteModel>) : FavouriteViewState()
        data class Empty(val error: String) : FavouriteViewState()
    }

}