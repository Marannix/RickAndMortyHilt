package com.example.rickandmorty.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.adapter.FavouriteAdapter
import com.example.rickandmorty.viewmodel.FavouriteViewModel
import kotlinx.android.synthetic.main.fragment_favourite.*

class FavouriteFragment : BaseFragment() {

    private val viewModel: FavouriteViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)
            .get(FavouriteViewModel::class.java)
    }

    private val adapter by lazy { FavouriteAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getFavourites()
        subscribeToFavouriteViewState()
        setFavouriteAdapter()
    }

    private fun subscribeToFavouriteViewState() {
        viewModel.getViewState().observe(this, Observer { viewState ->
            when (viewState) {
                FavouriteViewModel.FavouriteViewState.Loading -> {
                    //Loading Indicator
                }
                is FavouriteViewModel.FavouriteViewState.Content -> {
                    adapter.setFavouriteCharacters(viewState.listOfFavourites)
                }
                is FavouriteViewModel.FavouriteViewState.Empty -> {
                    // Show there are no favourites
                }
            }
        })
    }

    private fun setFavouriteAdapter() {
        favouritesRecyclerView.layoutManager = GridLayoutManager(context, 2)
        favouritesRecyclerView.adapter = adapter
    }
}
