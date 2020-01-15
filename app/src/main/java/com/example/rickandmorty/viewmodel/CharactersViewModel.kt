package com.example.rickandmorty.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.state.CharacterDataState
import com.example.rickandmorty.state.CharacterViewState
import com.example.rickandmorty.usecase.CharacterUseCase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val characterUseCase: CharacterUseCase
) : ViewModel() {

    private val disposables = CompositeDisposable()
    private val viewState = MutableLiveData<CharacterViewState>()

    fun onStart() {
        disposables.add(getCharacters())
    }
    //TODO: Handle error state when fails (no network or bad request..)
    private fun getCharacters(): Disposable {
        return characterUseCase.getCharacterDataState()
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
    }

    fun refresh() {
        val disposable =
            Completable.fromAction(characterUseCase::removeCharactersFromDatabase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    onStart()
                }

        disposables.add(disposable)
    }

    fun getViewState(): MutableLiveData<CharacterViewState> {
        return viewState
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}