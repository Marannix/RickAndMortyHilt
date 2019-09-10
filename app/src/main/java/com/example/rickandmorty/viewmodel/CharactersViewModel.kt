package com.example.rickandmorty.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.data.network.CharactersResponse
import com.example.rickandmorty.repository.CharactersRepository
import io.reactivex.Single

class CharactersViewModel(application: Application) : AndroidViewModel(application) {

    private val charactersRepository = CharactersRepository(application.baseContext)

    fun getCharacters() : List<CharactersResults> {
        return charactersRepository.getCharacters()
    }

    fun insertCharacters(characters: List<CharactersResults>) {
        return charactersRepository.insertCharacters(characters)
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