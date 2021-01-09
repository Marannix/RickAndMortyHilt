package com.example.rickandmorty.usecase

import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.repository.CharactersRepository
import io.reactivex.Observable
import javax.inject.Inject

class ObserveCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    operator fun invoke(): Observable<List<CharactersResults>> {
        return charactersRepository.getCharactersFromDbRx()
    }
}