package com.example.rickandmorty.dagger.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.viewmodel.CharactersViewModel
import com.example.rickandmorty.viewmodel.EpisodesViewModel
import com.example.rickandmorty.viewmodel.ViewModelFactory
import com.example.rickandmorty.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CharactersViewModel::class)
    internal abstract fun bindingCharactersViewModel(viewModel: CharactersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EpisodesViewModel::class)
    internal abstract fun bindingEpisodesViewModel(viewModel: EpisodesViewModel): ViewModel

}