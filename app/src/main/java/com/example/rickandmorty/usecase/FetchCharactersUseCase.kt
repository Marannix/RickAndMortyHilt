package com.example.rickandmorty.usecase

import com.example.rickandmorty.repository.CharactersRepository
import io.reactivex.Completable
import javax.inject.Inject

class FetchCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    operator fun invoke(): Completable {
        return charactersRepository.getCharactersFromApiRx()
    }
}