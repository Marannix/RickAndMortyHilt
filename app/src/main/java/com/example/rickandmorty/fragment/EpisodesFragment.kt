package com.example.rickandmorty.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.adapter.EpisodeAdapter
import com.example.rickandmorty.viewmodel.CharactersViewModel
import com.example.rickandmorty.viewmodel.EpisodesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_episodes.*

@AndroidEntryPoint
class EpisodesFragment : BaseFragment() {

    private val viewModel: EpisodesViewModel by viewModels()

    private val episodeAdapter = EpisodeAdapter()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getAllEpisodes()
        setEpisodeAdapter()
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
                    Log.d("data", viewstate.listOfEpisodes.toString())
                    episodeAdapter.setData(viewstate.listOfEpisodes)
                }
                is EpisodesViewModel.EpisodeViewState.Error -> {
                    //hide loading
                    // show error
                    Log.d("error", viewstate.message)
                }
            }
        })
    }

    private fun setEpisodeAdapter() {
        episodesRecyclerView.layoutManager = LinearLayoutManager(context)
        episodesRecyclerView.adapter = episodeAdapter
    }

}
