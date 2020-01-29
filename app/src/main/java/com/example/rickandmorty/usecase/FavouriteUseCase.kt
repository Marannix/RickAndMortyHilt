package com.example.rickandmorty.usecase

import com.example.rickandmorty.data.favourites.FavouriteModel
import com.example.rickandmorty.repository.FavouriteRepository
import io.reactivex.Observable
import javax.inject.Inject

class FavouriteUseCase @Inject constructor(
    private val favouriteRepository: FavouriteRepository
) {

    fun getFavouriteDataState(): Observable<FavouriteDataState> {
        return favouriteRepository.getFavourite()
            .map<FavouriteDataState> { listOfFavourites ->
                FavouriteDataState.Success(listOfFavourites)
            }
            .onErrorReturn { FavouriteDataState.Error("No favourites") }
    }

    sealed class FavouriteDataState {
        data class Success(val favourites: List<FavouriteModel>) : FavouriteDataState()
        data class Error(val errorMessage: String) : FavouriteDataState()
    }

}