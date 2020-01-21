package com.example.rickandmorty.data.favourites

import androidx.room.*
import io.reactivex.Single

@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavouriteCharacter(characters: FavouriteModel)

    @Delete
    fun deleteFavouriteCharacter(characters: FavouriteModel)

//     select 1 or * ?
    @Query("select exists (select * from favourites where id=:charactersId)")
    fun isFavourite(charactersId: String) : Boolean

    @Query("select * from favourites")
    fun getFavouriteCharacters(): Single<List<FavouriteModel>>
}