package com.example.rickandmorty.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.rickandmorty.R
import com.example.rickandmorty.viewmodel.FavouriteViewModel

class FavouriteFragment : BaseFragment() {

    private val viewModel: FavouriteViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)
            .get(FavouriteViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getFavourites()
        subscribeToFavouriteViewState()
    }

    private fun subscribeToFavouriteViewState() {
        viewModel.getViewState().observe(this, Observer { viewwState ->
            when (viewwState) {
                FavouriteViewModel.FavouriteViewState.Loading -> {
                    //Loading Indicator
                }
                is FavouriteViewModel.FavouriteViewState.Content -> {
                    Log.d("favourite list", viewwState.listOfFavourites.toString())
                }
                is FavouriteViewModel.FavouriteViewState.Empty -> {
                    // Show there are no favourites
                }
            }
        })
    }

}
