package com.example.rickandmorty.viewstate

import com.example.rickandmorty.common.AsyncResult
import com.example.rickandmorty.data.characters.CharactersResults

data class CharactersViewState(
    val fetchCharacters: AsyncResult<Nothing>? = null,
    val characters: AsyncResult<List<CharactersResults>>? = null
) {

    //TODO: A different type of loading for fetching from Api
    val isLoading: Boolean
        get() = fetchCharacters is AsyncResult.Loading

    val getCharacters
        get() = if (characters is AsyncResult.Success && characters.data != null) {
            characters.data
        } else null
}