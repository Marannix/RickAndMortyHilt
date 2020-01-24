package com.example.rickandmorty.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.rickandmorty.R
import com.example.rickandmorty.api.EpisodeApi
import com.example.rickandmorty.viewmodel.EpisodesViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EpisodesFragment : BaseFragment() {

    //todo move to base fragment right??
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var episodeApi : EpisodeApi

    private val disposables = CompositeDisposable()

    private val viewModel: EpisodesViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)
            .get(EpisodesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val disposable = episodeApi.getAllEpisodes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("hello", it.episodes[2].name)
            }, {
                Log.d("bad chief", it.message)
            })

        disposables.add(disposable)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_episodes, container, false)
    }

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }
}
