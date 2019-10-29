package com.example.rickandmorty.repository

import android.content.Context
import com.example.rickandmorty.api.CharactersApi
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.data.network.CharactersResponse
import com.example.rickandmorty.database.DatabaseHelper
import io.reactivex.Single
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val charactersApi: CharactersApi,
    private val context: Context) {

    private val databaseHelper = DatabaseHelper(context)

    fun fetchCharacters(page: Int) : Single<CharactersResponse> {
        return charactersApi.getCharacters(page)
    }

    fun fetchNextCharacters(nextCharactersUrl: String) : Single<CharactersResponse> {
        return charactersApi.getNextCharacters(nextCharactersUrl)
    }

    fun fetchPreviousCharacters(previousCharactersUrl: String) : Single<CharactersResponse> {
        return charactersApi.getPreviousCharacters(previousCharactersUrl)
    }

    fun insertCharacters(characters: List<CharactersResults>) {
        databaseHelper.insertCharacters(characters)
    }

    fun getCharacters() : List<CharactersResults> {
        return databaseHelper.getCharacters()
    }

}