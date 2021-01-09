package com.example.rickandmorty.dagger.modules

import androidx.fragment.app.FragmentActivity
import com.example.rickandmorty.activity.MainActivity
import com.example.rickandmorty.fragment.CharactersFragment
import com.example.rickandmorty.fragment.CharacterDetailsFragment
import com.example.rickandmorty.fragment.EpisodesFragment
import com.example.rickandmorty.fragment.FavouriteFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @Binds
    abstract fun provideMainActivity(activity: MainActivity): FragmentActivity

    @ContributesAndroidInjector
    abstract fun charactersDetailsFragment(): CharacterDetailsFragment

    @ContributesAndroidInjector
    abstract fun charactersRx2Fragment(): CharactersFragment

    @ContributesAndroidInjector
    abstract fun episodeFragment(): EpisodesFragment

    @ContributesAndroidInjector
    abstract fun favouriteFragment(): FavouriteFragment
}