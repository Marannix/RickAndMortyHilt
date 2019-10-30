package com.example.rickandmorty.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.data.network.CharactersResponse
import com.example.rickandmorty.repository.CharactersRepository
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val charactersRepository: CharactersRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()
    val viewState = MutableLiveData<List<CharactersResults>>()

    init {
        getCharacters2()
    }

    //TODO: Handle error state when fails (no network or bad request..)
    fun getCharacters2() {
        disposables.add(
            charactersRepository.getCharacters().subscribe{
                viewState.postValue(it)
            }
        )
    }

//    fun getCharacters(): List<CharactersResults> {
//        return charactersRepository.getCharactersFromDb()
//    }


//    fun fetchCharacters(page: Int): Single<CharactersResponse> {
//        return charactersRepository.getCharactersFromApi(page)
//    }

    fun getNextCharacters(url: String): Single<CharactersResponse> {
        return charactersRepository.fetchNextCharacters(url)
    }

    fun getPreviousCharacters(url: String): Single<CharactersResponse> {
        return charactersRepository.fetchPreviousCharacters(url)
    }
}