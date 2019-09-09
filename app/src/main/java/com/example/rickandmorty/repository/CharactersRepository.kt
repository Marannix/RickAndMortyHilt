package com.example.rickandmorty.repository

import com.example.rickandmorty.data.network.ApiService
import com.example.rickandmorty.data.network.CharactersResponse
import com.example.rickandmorty.data.network.EpisodeResponse
import io.reactivex.Single

class CharactersRepository {

    private val apiService = object : ApiService {}

    fun fetchCharacters(page: Int) : Single<CharactersResponse> {
        return apiService.charactersApi().getCharacters(page)
    }

    fun fetchNextCharacters(nextCharactersUrl: String) : Single<CharactersResponse> {
        return apiService.loadMoreCharactersApi(nextCharactersUrl).getNextCharacters()
    }

    fun fetchPreviousCharacters(nextCharactersUrl: String) : Single<CharactersResponse> {
        return apiService.loadMoreCharactersApi(nextCharactersUrl).getPreviousCharacters()
    }

}