package com.example.rickandmorty.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.rickandmorty.data.characters.CharactersResults

class DatabaseHelper(private val context: Context) {

    private var database: ApplicationDatabase = ApplicationDatabase.DatabaseProvider.getInstance(context)!!

    fun retrieveDatabase() {
        database = ApplicationDatabase.DatabaseProvider.getInstance(context)!!
    }

    fun getCharacters(): List<CharactersResults> {
        return database.charactersDao().getAllCharacters()
    }

    fun insertCharacters(characters: List<CharactersResults>) {
        database.charactersDao().insertCharacters(characters)
    }

}