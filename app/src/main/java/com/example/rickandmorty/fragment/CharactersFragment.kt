package com.example.rickandmorty.fragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.adapter.CharactersAdapter
import com.example.rickandmorty.viewmodel.CharactersViewModel
import com.example.rickandmorty.viewmodel.CharactersViewModel.CharacterViewEvent.GenericErrorEvent
import com.example.rickandmorty.common.AutoCompositeDisposable
import com.example.rickandmorty.common.addTo
import com.example.rickandmorty.dialog.FullscreenLoadingDialog
import kotlinx.android.synthetic.main.fragment_characters.*

private const val MOBILE_SIZE = 1
private const val TABLET_SIZE = 2

class CharactersFragment : BaseFragment() {

    private val charactersAdapter = CharactersAdapter()
    private var isTablet: Boolean = false

    private lateinit var loadingDialog: Dialog
    private val disposable: AutoCompositeDisposable by lazy { AutoCompositeDisposable(lifecycle) }

    private val viewModel: CharactersViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)
            .get(CharactersViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = FullscreenLoadingDialog(requireContext()).apply {
            setCanceledOnTouchOutside(false)
        }
    }

    private fun setCharacterAdapter() {
        charactersRecyclerView.layoutManager = GridLayoutManager(context, isTablet())
        charactersRecyclerView.adapter = charactersAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


            setCharacterAdapter()

        viewModel.events()
            .subscribe {
                when (it) {
                    is GenericErrorEvent -> {
                        genericError.visibility = View.VISIBLE
                        refreshButton.visibility = View.VISIBLE
                        Log.d("error", it.error.toString())
                        Toast.makeText(requireContext(), it.error.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }.addTo(disposable)

        viewModel.states()
            .distinctUntilChanged()
            .subscribe { state ->
                state.isLoading.let {
                    when {
                        it -> { loadingDialog.show() }
                        else -> { loadingDialog.hide() }
                    }
                }

                state.getCharacters?.let {
                    charactersAdapter.setData(it)
                }
            }
            .addTo(disposable)

    }
    private fun isTablet(): Int {
        isTablet = resources.getBoolean(R.bool.isTablet)
        return if (isTablet) {
            TABLET_SIZE
        } else {
            MOBILE_SIZE
        }
    }

    override fun onPause() {
        super.onPause()
        (charactersRecyclerView.layoutManager as GridLayoutManager).onSaveInstanceState()
    }
}