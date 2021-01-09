package com.example.rickandmorty.viewmodel

import com.example.rickandmorty.viewstate.CharactersViewState
import com.example.rickandmorty.common.AsyncResult
import com.example.rickandmorty.common.mapToAsyncResult
import com.example.rickandmorty.usecase.FetchCharactersUseCase
import com.example.rickandmorty.usecase.ObserveCharactersUseCase
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    characterUseCase: FetchCharactersUseCase,
    observeCharactersUseCase: ObserveCharactersUseCase
) : RxViewModelStore<CharactersViewState, CharactersViewModel.CharacterRxViewEvent>(
    CharactersViewState()
) {

    init {
        characterUseCase.invoke()
            .mapToAsyncResult()
            .subscribeOn(Schedulers.io())
            .subscribe { result ->
                applyState(Reducer { it.copy(fetchCharacters = result) })
            }.addDisposable()

        observeCharactersUseCase.invoke()
            .mapToAsyncResult()
            .subscribeOn(Schedulers.io())
            .subscribe { result ->
                applyState(Reducer { it.copy(characters = result) })
                if (result is AsyncResult.Error) {
                    publish(CharacterRxViewEvent.GenericErrorEvent(result.error))
                }
            }.addDisposable()
   }

    sealed class CharacterRxViewEvent {
        data class GenericErrorEvent(val error: Throwable?) : CharacterRxViewEvent()
    }
}