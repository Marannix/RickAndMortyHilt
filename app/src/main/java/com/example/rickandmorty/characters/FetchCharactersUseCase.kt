package com.example.rickandmorty.characters

import com.example.rickandmorty.repository.CharactersRepository
import com.example.rickandmorty.state.CharacterDataState
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class FetchCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    operator fun invoke(): Completable {
        return charactersRepository.getCharactersFromApiRx()
    }
}