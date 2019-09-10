package com.example.rickandmorty.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.data.network.ApiService
import com.example.rickandmorty.data.network.CharactersResponse
import com.example.rickandmorty.data.network.EpisodeResponse
import com.example.rickandmorty.database.DatabaseHelper
import io.reactivex.Single

class CharactersRepository(val context: Context) {

    private val apiService = object : ApiService {}
    private val databaseHelper = DatabaseHelper(context)

    fun fetchCharacters(page: Int) : Single<CharactersResponse> {
        return apiService.charactersApi().getCharacters(page)
    }

    fun fetchNextCharacters(nextCharactersUrl: String) : Single<CharactersResponse> {
        return apiService.loadMoreCharactersApi(nextCharactersUrl).getNextCharacters()
    }

    fun fetchPreviousCharacters(nextCharactersUrl: String) : Single<CharactersResponse> {
        return apiService.loadMoreCharactersApi(nextCharactersUrl).getPreviousCharacters()
    }

    fun insertCharacters(characters: List<CharactersResults>) {
        databaseHelper.insertCharacters(characters)
    }

    fun getCharacters() : List<CharactersResults> {
        return databaseHelper.getCharacters()
    }

}