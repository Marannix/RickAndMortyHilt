package com.example.rickandmorty.dagger.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.characters.CharactersRxViewModel
import com.example.rickandmorty.dagger.ViewModelFactory
import com.example.rickandmorty.dagger.ViewModelKey
import com.example.rickandmorty.viewmodel.CharactersViewModel
import com.example.rickandmorty.viewmodel.EpisodesViewModel
import com.example.rickandmorty.viewmodel.FavouriteViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

//    @Binds
//    internal abstract fun bindingCharactersViewModelFactory(): CharactersRxViewModel.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CharactersViewModel::class)
    internal abstract fun bindingCharactersViewModel(viewModel: CharactersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharactersRxViewModel::class)
    internal abstract fun bindingCharactersRxViewModel(viewModel: CharactersRxViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EpisodesViewModel::class)
    internal abstract fun bindingEpisodesViewModel(viewModel: EpisodesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouriteViewModel::class)
    internal abstract fun bindingFavouritesViewModel(viewModel: FavouriteViewModel): ViewModel
}