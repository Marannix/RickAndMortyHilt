package com.example.rickandmorty.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.rickandmorty.R
import com.example.rickandmorty.viewmodel.EpisodesViewModel
import javax.inject.Inject

class EpisodesFragment : BaseFragment() {

    //todo move to base fragment right??
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: EpisodesViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)
            .get(EpisodesViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getAllEpisodes()
        subscribeToEpisodeViewState()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_episodes, container, false)
    }

    private fun subscribeToEpisodeViewState() {
        viewModel.episodeViewState.observe(this, Observer { viewstate ->
            when (viewstate) {
                EpisodesViewModel.EpisodeViewState.Loading -> {
                    //show loading
                    Log.d("loading", "loading chief")
                }
                is EpisodesViewModel.EpisodeViewState.Content -> {
                    //hide loading
                    Log.d("content", viewstate.listOfEpisodes.toString())
                }
                is EpisodesViewModel.EpisodeViewState.Error -> {
                    //hide loading
                    // show error
                    Log.d("error", viewstate.message)
                }
            }
        })
    }

}
