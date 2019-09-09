package com.example.rickandmorty.fragment

import android.os.Bundle
import android.util.Log
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
import java.util.*
import kotlin.collections.ArrayList

const val FIRST_PAGE = 1

class CharactersFragment : Fragment() {

    private val disposables = CompositeDisposable()
    private val charactersAdapter = CharactersAdapter()
    private lateinit var characters : List<CharactersResults>
    private lateinit var charactersPageInfo : CharactersPageInfo

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("key_characters", ArrayList<CharactersResults>(characters))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
                    characters = it.charactersResults
                    charactersPageInfo = it.characterPageInfo

                    onRetrieveCharactersSuccess(it.charactersResults)
                    setNextButton(it.characterPageInfo)
                    setPreviousButton(it.characterPageInfo)
                },
                { onRetrieveCharactersError(it.message) }
            )

        disposables.add(disposable)
    }

    private fun onRetrieveCharactersSuccess(charactersResults: List<CharactersResults>) {
        charactersAdapter.setData(charactersResults)
    }

    private fun onRetrieveCharactersError(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        Log.e("retrieveCharactersError", message)
    }

    private fun setNextButton(characterPageInfo: CharactersPageInfo) {
        if (characterPageInfo.next.isEmpty()) {
            nextButton.visibility = View.INVISIBLE
            return
        }

        nextButton.visibility = View.VISIBLE
        nextButton.setOnClickListener {
            loadNextCharacters(characterPageInfo.next)
        }

    }

    private fun setPreviousButton(characterPageInfo: CharactersPageInfo) {
        if (characterPageInfo.prev.isEmpty()) {
            previousButton.visibility = View.INVISIBLE
            return
        }

        previousButton.visibility = View.VISIBLE
        previousButton.setOnClickListener {
            loadPreviousCharacters(characterPageInfo.prev)
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
                { onRetrieveCharactersError(it.message) }
            )

        disposables.add(disposable)
    }

    private fun loadPreviousCharacters(previous: String) {
        val disposable = CharactersRepository().fetchPreviousCharacters(previous)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    onRetrieveCharactersSuccess(it.charactersResults)
                    setNextButton(it.characterPageInfo)
                    setPreviousButton(it.characterPageInfo)
                },
                { onRetrieveCharactersError(it.message) }
            )

        disposables.add(disposable)
    }

    override fun onStop() {
        disposables.clear()
        super.onStop()
    }
}