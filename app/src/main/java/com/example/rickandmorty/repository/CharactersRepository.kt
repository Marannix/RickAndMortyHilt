package com.example.rickandmorty.repository

import com.example.rickandmorty.api.CharactersApi
import com.example.rickandmorty.data.characters.CharactersDao
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.data.network.CharactersResponse
import io.reactivex.Single
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val charactersDao: CharactersDao,
    private val charactersApi: CharactersApi) {

    fun fetchCharacters(page: Int) : Single<CharactersResponse> {
        return charactersApi.getCharacters(page)
    }

    fun fetchNextCharacters(nextCharactersUrl: String) : Single<CharactersResponse> {
        return charactersApi.getNextCharacters(nextCharactersUrl)
    }

    fun fetchPreviousCharacters(previousCharactersUrl: String) : Single<CharactersResponse> {
        return charactersApi.getPreviousCharacters(previousCharactersUrl)
    }

    fun storeCharactersInDb(characters: List<CharactersResults>) {
        charactersDao.insertCharacters(characters)
    }

    fun getCharactersFromDb() : List<CharactersResults> {
        return charactersDao.getCharacters()
    }

}