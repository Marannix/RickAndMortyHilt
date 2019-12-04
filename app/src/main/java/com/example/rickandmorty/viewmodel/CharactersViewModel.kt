package com.example.rickandmorty.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.state.CharacterDataState
import com.example.rickandmorty.state.CharacterViewState
import com.example.rickandmorty.usecase.CharacterUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val characterUseCase: CharacterUseCase
) : ViewModel() {

    private val disposables = CompositeDisposable()
    val viewState = MutableLiveData<CharacterViewState>()

    //TODO: Handle error state when fails (no network or bad request..)r
    fun getCharacters() {
        disposables.add(
            characterUseCase.getCharacterDataState()
                .observeOn(AndroidSchedulers.mainThread())
                .map { characterDataState ->
                    return@map when (characterDataState) {
                        is CharacterDataState.Success -> {
                            CharacterViewState.ShowCharacters(characterDataState.characters)
                        }
                        is CharacterDataState.Error -> {
                            if (viewState.value is CharacterViewState.ShowCharacters) {
                                viewState.value
                            } else {
                                CharacterViewState.ShowError(characterDataState.errorMessage)
                            }

                        }
                    }
                }
                .doOnSubscribe { viewState.value = CharacterViewState.Loading }
                .subscribe { viewState ->
                    this.viewState.value = viewState
                }
        )
    }
}