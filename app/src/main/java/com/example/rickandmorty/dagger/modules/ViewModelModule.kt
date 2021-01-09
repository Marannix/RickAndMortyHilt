package com.example.rickandmorty.dagger.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.viewmodel.CharactersViewModel
import com.example.rickandmorty.dagger.ViewModelFactory
import com.example.rickandmorty.dagger.ViewModelKey
import com.example.rickandmorty.viewmodel.EpisodesViewModel
import com.example.rickandmorty.viewmodel.FavouriteViewModel
import com.example.rickandmorty.viewmodel.SettingsViewModel
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
    internal abstract fun bindingCharactersRxViewModel(viewModel: CharactersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    internal abstract fun bindingSettingsViewModel(viewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EpisodesViewModel::class)
    internal abstract fun bindingEpisodesViewModel(viewModel: EpisodesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouriteViewModel::class)
    internal abstract fun bindingFavouritesViewModel(viewModel: FavouriteViewModel): ViewModel
}