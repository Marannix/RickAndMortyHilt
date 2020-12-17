package com.example.rickandmorty.characters

import com.example.rickandmorty.common.AsyncResult
import com.example.rickandmorty.data.characters.CharactersResults

data class CharacterRxViewState(
    val fetchCharacters: AsyncResult<Nothing>? = null,
    val characters: AsyncResult<List<CharactersResults>>? = null
) {

    val isLoading: Boolean
        get() = fetchCharacters is AsyncResult.Loading

    val getCharacters
        get() = if (characters is AsyncResult.Success && characters.data != null) {
            characters.data
        } else null
}