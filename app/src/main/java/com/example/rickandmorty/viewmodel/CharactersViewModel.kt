package com.example.rickandmorty.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.repository.CharactersRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val charactersRepository: CharactersRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()
    val viewState = MutableLiveData<List<CharactersResults>>()

    // TODO: Remove init and call manually
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

//    fun getNextCharacters() {
//
//    }

//    fun getCharacters(): List<CharactersResults> {
//        return charactersRepository.getCharactersFromDb()
//    }


//    fun fetchCharacters(page: Int): Single<CharactersResponse> {
//        return charactersRepository.getCharactersFromApi(page)
//    }

//    fun getNextCharacters(url: String): Single<CharactersResponse> {
//        return charactersRepository.fetchNextCharacters(url)
//    }

//    fun getPreviousCharacters(url: String): Single<CharactersResponse> {
//        return charactersRepository.fetchPreviousCharacters(url)
//    }
}