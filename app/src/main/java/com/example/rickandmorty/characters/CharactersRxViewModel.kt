package com.example.rickandmorty.characters

import com.example.rickandmorty.common.AsyncResult
import com.example.rickandmorty.common.mapToAsyncResult
import com.example.rickandmorty.viewmodel.Reducer
import com.example.rickandmorty.viewmodel.RxViewModelStore
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CharactersRxViewModel @Inject constructor(
    characterUseCase: FetchCharactersUseCase,
    observeCharactersUseCase: ObserveCharactersUseCase
) : RxViewModelStore<CharacterRxViewState, CharactersRxViewModel.CharacterRxViewEvent>(CharacterRxViewState()) {

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