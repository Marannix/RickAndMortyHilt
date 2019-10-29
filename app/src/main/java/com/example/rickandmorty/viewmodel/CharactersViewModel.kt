package com.example.rickandmorty.viewmodel

import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.data.network.CharactersResponse
import com.example.rickandmorty.repository.CharactersRepository
import io.reactivex.Single
import javax.inject.Inject

class CharactersViewModel @Inject constructor(val charactersRepository: CharactersRepository) : ViewModel() {

    fun getCharacters(): List<CharactersResults> {
        return charactersRepository.getCharactersFromDb()
    }

    fun insertCharacters(characters: List<CharactersResults>) {
        return charactersRepository.storeCharactersInDb(characters)
    }

    fun fetchCharacters(page: Int): Single<CharactersResponse> {
        return charactersRepository.fetchCharacters(page)
    }

    fun getNextCharacters(url: String): Single<CharactersResponse> {
        return charactersRepository.fetchNextCharacters(url)
    }

    fun getPreviousCharacters(url: String): Single<CharactersResponse> {
        return charactersRepository.fetchPreviousCharacters(url)
    }
}