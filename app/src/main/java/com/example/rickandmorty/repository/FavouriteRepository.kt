package com.example.rickandmorty.repository

import com.example.rickandmorty.data.favourites.FavouriteDao
import com.example.rickandmorty.data.favourites.FavouriteModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavouriteRepository @Inject constructor(
    private val favouriteDao: FavouriteDao
) {

    fun storeInFavourite(character: FavouriteModel) {
        favouriteDao.insertFavouriteCharacter(character)
    }

    fun removeFromFavourites(character: FavouriteModel) {
        favouriteDao.deleteFavouriteCharacter(character)
    }

    fun isFavourite(characterId: String) : Boolean {
        return favouriteDao.isFavourite(characterId)
    }

    fun getFavourite(): Observable<List<FavouriteModel>> {
        return favouriteDao.getFavouriteCharacters().subscribeOn(Schedulers.io())
            .toObservable()
            .flatMap {
                return@flatMap if (it.isEmpty()) {
                    Observable.empty()
                } else {
                    Observable.just(it)
                }
            }
    }
}