package com.example.rickandmorty.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.rickandmorty.R
import com.example.rickandmorty.repository.CharactersRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

const val FIRST_PAGE = 1

class CharactersFragment : Fragment() {

    private val disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val disposable = CharactersRepository().fetchCharacters(FIRST_PAGE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Toast.makeText(requireContext(), it.charactersResults[1].id, Toast.LENGTH_SHORT).show() },
                { Toast.makeText(requireContext(), "yikes", Toast.LENGTH_SHORT).show() }
            )

        disposables.add(disposable)
    }

    override fun onStop() {
        disposables.clear()
        super.onStop()
    }
}
