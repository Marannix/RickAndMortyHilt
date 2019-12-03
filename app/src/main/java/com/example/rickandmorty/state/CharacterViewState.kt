package com.example.rickandmorty.state

import com.example.rickandmorty.data.characters.CharactersResults

sealed class CharacterViewState {
    object Loading : CharacterViewState()
    data class ShowCharacters(val characters: List<CharactersResults>) : CharacterViewState()
    data class ShowError(val errorMessage: String?) : CharacterViewState()
}