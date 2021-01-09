package com.example.rickandmorty.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.rickandmorty.R
import com.example.rickandmorty.viewmodel.SettingsViewModel

class SettingsFragment : BaseFragment() {

    private val viewModel: SettingsViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)
            .get(SettingsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

}