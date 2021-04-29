package com.example.rickandmorty.viewmodel

import com.example.rickandmorty.viewstate.CharactersViewState
import com.example.rickandmorty.common.AsyncResult
import com.example.rickandmorty.common.mapToAsyncResult
import com.example.rickandmorty.usecase.FetchCharactersUseCase
import com.example.rickandmorty.usecase.ObserveCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    characterUseCase: FetchCharactersUseCase,
    observeCharactersUseCase: ObserveCharactersUseCase
) : RxViewModelStore<CharactersViewState, CharactersViewModel.CharacterViewEvent>(
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
                    publish(CharacterViewEvent.GenericErrorEvent(result.error))
                }
            }.addDisposable()
   }

    sealed class CharacterViewEvent {
        data class GenericErrorEvent(val error: Throwable?) : CharacterViewEvent()
    }
}