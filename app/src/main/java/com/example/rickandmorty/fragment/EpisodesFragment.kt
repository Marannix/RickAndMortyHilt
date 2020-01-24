package com.example.rickandmorty.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.rickandmorty.R
import com.example.rickandmorty.viewmodel.EpisodesViewModel
import javax.inject.Inject

class EpisodesFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: EpisodesViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)
            .get(EpisodesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_episodes, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = EpisodesFragment()
    }
}
