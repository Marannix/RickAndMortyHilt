package com.example.rickandmorty.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.adapter.CharactersAdapter
import com.example.rickandmorty.data.characters.CharactersPageInfo
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.viewmodel.CharactersViewModel
import com.example.rickandmorty.viewmodel.EpisodesViewModel
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_characters.*

const val FIRST_PAGE = 1
const val MOBILE_SIZE = 1
const val TABLET_SIZE = 2

class CharactersFragment : Fragment() {

    private val disposables = CompositeDisposable()
    private val charactersAdapter = CharactersAdapter()
    private var isTablet: Boolean = false
    private lateinit var characters: List<CharactersResults>
    private lateinit var charactersPageInfo: CharactersPageInfo
    private lateinit var charactersViewModel: CharactersViewModel
    private lateinit var episodesViewModel: EpisodesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        charactersViewModel = ViewModelProviders.of(this)
            .get(CharactersViewModel::class.java)
        episodesViewModel = ViewModelProviders.of(this)
            .get(EpisodesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateToolbar()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setCharacterAdapter(isTablet())
    }

    private fun setCharacterAdapter(isTablet : Int) {
        charactersRecyclerView.layoutManager = GridLayoutManager(context, isTablet)
        charactersRecyclerView.adapter = charactersAdapter

        val disposable = charactersViewModel.fetchCharacters(FIRST_PAGE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    characters = it.charactersResults
                    charactersPageInfo = it.characterPageInfo
                    refreshButton.visibility = View.GONE
                    onRetrieveCharactersSuccess(it.charactersResults)
                    setNextButton(it.characterPageInfo)
                    setPreviousButton(it.characterPageInfo)
                },
                {
                    onRetrieveCharactersError()
                    charactersAdapter.setData(charactersViewModel.getCharacters())
                }
            )

        disposables.add(disposable)
    }

    private fun onRetrieveCharactersSuccess(charactersResults: List<CharactersResults>) {
        charactersViewModel.insertCharacters(charactersResults)
        charactersAdapter.setData(charactersResults)
    }

    private fun onRetrieveCharactersError() {
        Snackbar.make(view!!, getString(R.string.generic_error_message), Snackbar.LENGTH_SHORT).show()
        refreshButton.visibility = View.VISIBLE
        refreshButton.setOnClickListener {
            setCharacterAdapter(isTablet())
        }
        nextButton.visibility = View.GONE
        previousButton.visibility = View.GONE
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
        val disposable = charactersViewModel.getNextCharacters(next)
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

    private fun loadPreviousCharacters(previous: String) {
        val disposable = charactersViewModel.getPreviousCharacters(previous)
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

    private fun isTablet(): Int {
        isTablet = resources.getBoolean(R.bool.isTablet)
        return if (isTablet) {
            TABLET_SIZE
        } else {
            MOBILE_SIZE
        }
    }

    private fun updateToolbar() {
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.toolbar_title)
    }

    override fun onStop() {
        disposables.clear()
        super.onStop()
    }
}