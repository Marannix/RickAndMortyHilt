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
        
        charactersRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        charactersRecyclerView.adapter = charactersAdapter

        val disposable = CharactersRepository().fetchCharacters(FIRST_PAGE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onRetrieveCharactersSuccess(it.charactersResults) },
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

    override fun onStop() {
        disposables.clear()
        super.onStop()
    }
}
