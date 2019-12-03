package com.example.rickandmorty.state

import com.example.rickandmorty.data.characters.CharactersResults

sealed class CharacterDataState {
    data class Success(val characters: List<CharactersResults>) : CharacterDataState()
    data class Error(val errorMessage: String?) : CharacterDataState()
}