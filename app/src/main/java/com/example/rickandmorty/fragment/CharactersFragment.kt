package com.example.rickandmorty.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.adapter.CharactersAdapter
import com.example.rickandmorty.data.characters.CharactersPageInfo
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.repository.CharactersRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_characters.*

const val FIRST_PAGE = 1

class CharactersFragment : Fragment() {

    private val disposables = CompositeDisposable()
    private val charactersAdapter = CharactersAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCharacterAdapter()
    }

    private fun setCharacterAdapter() {
        charactersRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        charactersRecyclerView.adapter = charactersAdapter

        val disposable = CharactersRepository().fetchCharacters(FIRST_PAGE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    onRetrieveCharactersSuccess(it.charactersResults)
                    setNextButton(it.characterPageInfo)
                    setPreviousButton(it.characterPageInfo)
                },
                { onRetrieveCharactersError() }
            )

        disposables.add(disposable)
    }

    private fun onRetrieveCharactersSuccess(charactersResults: List<CharactersResults>) {
        charactersAdapter.setData(charactersResults)
    }

    private fun onRetrieveCharactersError() {
        Toast.makeText(requireContext(), "Something went wrong while retrieving characters", Toast.LENGTH_SHORT).show()
    }

    private fun setNextButton(characterPageInfo: CharactersPageInfo) {
        if (characterPageInfo.next.isNotEmpty()) {
            nextButton.visibility = View.VISIBLE
            nextButton.setOnClickListener {
                loadNextCharacters(characterPageInfo.next)
            }
        } else {
            nextButton.visibility = View.INVISIBLE
        }
    }

    private fun setPreviousButton(characterPageInfo: CharactersPageInfo) {
        if (characterPageInfo.previous?.isEmpty()) {

        } else {

        }
    }

    private fun loadNextCharacters(next: String) {
        val disposable = CharactersRepository().fetchNextCharacters(next)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    onRetrieveCharactersSuccess(it.charactersResults)
                    setNextButton(it.characterPageInfo)
                    setPreviousButton(it.characterPageInfo)
                },
                { onRetrieveCharactersError() }
            )

        disposables.add(disposable)
    }

    override fun onStop() {
        disposables.clear()
        super.onStop()
    }
}