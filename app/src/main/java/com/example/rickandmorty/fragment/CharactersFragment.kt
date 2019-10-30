package com.example.rickandmorty.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.adapter.CharactersAdapter
import com.example.rickandmorty.data.characters.CharactersPageInfo
import com.example.rickandmorty.viewmodel.CharactersViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_characters.*
import javax.inject.Inject

const val FIRST_PAGE = 1
const val MOBILE_SIZE = 1
const val TABLET_SIZE = 2

class CharactersFragment : BaseFragment() {

    private val disposables = CompositeDisposable()
    private val charactersAdapter = CharactersAdapter()
    private var isTablet: Boolean = false

//    private lateinit var characters: List<CharactersResults>
    private lateinit var charactersPageInfo: CharactersPageInfo

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val charactersViewModel: CharactersViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)
            .get(CharactersViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateToolbar()
        setCharacterAdapter(isTablet())
        subscribeToCharacterViewState()
    }

    private fun subscribeToCharacterViewState() {
        charactersViewModel.viewState.observe(this, Observer { list ->
            charactersAdapter.setData(list)
        })
    }

    private fun setCharacterAdapter(isTablet : Int) {
        charactersRecyclerView.layoutManager = GridLayoutManager(context, isTablet)
        charactersRecyclerView.adapter = charactersAdapter
    }

//    private fun setCharacterAdapter2(isTablet : Int) {
//        charactersRecyclerView.layoutManager = GridLayoutManager(context, isTablet)
//        charactersRecyclerView.adapter = charactersAdapter

//        val disposable = charactersViewModel.fetchCharacters(FIRST_PAGE)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    genericError.visibility = View.GONE
//                    refreshButton.visibility = View.GONE
//                    characters = it.charactersResults
//                    charactersPageInfo = it.characterPageInfo
//                    onRetrieveCharactersSuccess(it.charactersResults)
//                    setNextButton(it.characterPageInfo)
//                    setPreviousButton(it.characterPageInfo)
//                },
//                {
//                    Toast.makeText(requireContext(), "TODO!!!", Toast.LENGTH_SHORT).show()
////                    onRetrieveCharactersError()
////                    charactersAdapter.setData(charactersViewModel.getCharacters())
////                    // TODO: Fix Error when no characters in dp / Api
////                    if (charactersViewModel.getCharacters().isEmpty()) {
////                        genericError.visibility = View.VISIBLE
////                    } else {
////                        genericError.visibility = View.GONE
////                    }
//                }
//            )

//        disposables.add(disposable)
//    }

//    private fun onRetrieveCharactersSuccess(charactersResults: List<CharactersResults>) {
////        charactersViewModel.insertCharacters(charactersResults)
//        charactersAdapter.setData(charactersResults)
//    }

//    private fun onRetrieveCharactersError() {
//        Snackbar.make(view!!, getString(R.string.generic_error_message), Snackbar.LENGTH_SHORT).show()
//        refreshButton.visibility = View.VISIBLE
//        refreshButton.setOnClickListener {
//            setCharacterAdapter(isTablet())
//        }
//        nextButton.visibility = View.GONE
//        previousButton.visibility = View.GONE
//    }

//    private fun setNextButton(characterPageInfo: CharactersPageInfo) {
//        if (characterPageInfo.next.isEmpty()) {
//            nextButton.visibility = View.INVISIBLE
//            return
//        }
//
//        nextButton.visibility = View.VISIBLE
//        nextButton.setOnClickListener {
//            loadNextCharacters(characterPageInfo.next)
//        }
//
//    }

//    private fun setPreviousButton(characterPageInfo: CharactersPageInfo) {
//        if (characterPageInfo.prev.isEmpty()) {
//            previousButton.visibility = View.INVISIBLE
//            return
//        }
//
//        previousButton.visibility = View.VISIBLE
//        previousButton.setOnClickListener {
//            loadPreviousCharacters(characterPageInfo.prev)
//        }
//
//    }

//    private fun loadNextCharacters(next: String) {
//        val disposable = charactersViewModel.getNextCharacters(next)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    onRetrieveCharactersSuccess(it.charactersResults)
////                    setNextButton(it.characterPageInfo)
////                    setPreviousButton(it.characterPageInfo)
//                },
//                { onRetrieveCharactersError() }
//            )
//
//        disposables.add(disposable)
//    }

//    private fun loadPreviousCharacters(previous: String) {
//        val disposable = charactersViewModel.getPreviousCharacters(previous)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    onRetrieveCharactersSuccess(it.charactersResults)
////                    setNextButton(it.characterPageInfo)
////                    setPreviousButton(it.characterPageInfo)
//                },
//                { onRetrieveCharactersError() }
//            )
//
//        disposables.add(disposable)
//    }

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