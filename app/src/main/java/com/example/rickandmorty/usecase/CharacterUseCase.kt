package com.example.rickandmorty.usecase

import com.example.rickandmorty.repository.CharactersRepository
import com.example.rickandmorty.state.CharacterDataState
import io.reactivex.Observable
import javax.inject.Inject

class CharacterUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {

    fun getCharacterDataState(): Observable<CharacterDataState> {
        return charactersRepository.getCharacters()
            .map<CharacterDataState> { listOfCharacters ->
                CharacterDataState.Success(listOfCharacters)
            }
            .onErrorReturn { error -> CharacterDataState.Error(error.message) }
    }

    fun removeCharactersFromDatabase() {
        charactersRepository.removeCharactersFromDb()
    }
}