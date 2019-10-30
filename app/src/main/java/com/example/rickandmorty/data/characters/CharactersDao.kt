package com.example.rickandmorty.data.characters

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface CharactersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCharacters(characters: List<CharactersResults>)

    @Query("select * from characters")
    fun getCharacters(): Single<List<CharactersResults>>
}