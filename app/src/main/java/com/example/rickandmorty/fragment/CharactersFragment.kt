package com.example.rickandmorty.fragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.adapter.CharactersAdapter
import com.example.rickandmorty.dialog.FullscreenLoadingDialog
import com.example.rickandmorty.state.CharacterViewState
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

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var loadingDialog: Dialog

    private val charactersViewModel: CharactersViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)
            .get(CharactersViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = FullscreenLoadingDialog(requireContext()).apply {
            setCanceledOnTouchOutside(false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateToolbar()
        setCharacterAdapter()
        charactersViewModel.getCharacters()
        subscribeToCharacterViewState()
    }

    private fun subscribeToCharacterViewState() {
        charactersViewModel.viewState.observe(this, Observer { viewState ->
            when (viewState) {
                is CharacterViewState.Loading -> {
                    loadingDialog.show()
                }
                is CharacterViewState.ShowCharacters -> {
                    loadingDialog.hide()
                    charactersAdapter.setData(viewState.characters)
                }
                is CharacterViewState.ShowError -> {
                    loadingDialog.hide()
                    // TODO need to add an error state (when no characters have been fetched, probably due to no network)
                    genericError.visibility = View.VISIBLE
                    refreshButton.visibility = View.VISIBLE
                    Log.d("error", viewState.errorMessage)
                    Toast.makeText(requireContext(), viewState.errorMessage, Toast.LENGTH_SHORT).show()
                }

            }

        })
    }

    private fun setCharacterAdapter() {
        charactersRecyclerView.layoutManager = GridLayoutManager(context, isTablet())
        charactersRecyclerView.adapter = charactersAdapter
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