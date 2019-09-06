package com.example.rickandmorty.repository

import com.example.rickandmorty.data.network.ApiService
import com.example.rickandmorty.data.network.CharactersResponse
import io.reactivex.Single

class CharactersRepository {

    private val apiService = object : ApiService {}

    fun fetchCharacters(page: Int) : Single<CharactersResponse> {
        return apiService.charactersApi().getCharacters(page)
    }
}