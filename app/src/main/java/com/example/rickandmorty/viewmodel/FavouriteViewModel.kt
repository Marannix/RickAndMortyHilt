package com.example.rickandmorty.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.favourites.FavouriteModel
import com.example.rickandmorty.usecase.FavouriteUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class FavouriteViewModel @Inject constructor(
    private val favouriteUseCase: FavouriteUseCase
) : ViewModel() {

    private val disposables = CompositeDisposable()
    private val viewState = MutableLiveData<FavouriteViewState>()

    fun getFavourites() {
        val disposable = favouriteUseCase.getFavouriteDataState()
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
            }
        disposables.add(disposable)
    }

    fun getViewState(): MutableLiveData<FavouriteViewState> {
        return viewState
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    sealed class FavouriteViewState {
        object Loading : FavouriteViewState()
        data class Content(val listOfFavourites: List<FavouriteModel>) : FavouriteViewState()
        data class Empty(val error: String) : FavouriteViewState()
    }

}