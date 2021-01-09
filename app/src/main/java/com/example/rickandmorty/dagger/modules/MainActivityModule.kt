package com.example.rickandmorty.dagger.modules

import androidx.fragment.app.FragmentActivity
import com.example.rickandmorty.activity.MainActivity
import com.example.rickandmorty.fragment.*
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
    abstract fun charactersFragment(): CharactersFragment

    @ContributesAndroidInjector
    abstract fun episodeFragment(): EpisodesFragment

    @ContributesAndroidInjector
    abstract fun favouriteFragment(): FavouriteFragment

    @ContributesAndroidInjector
    abstract fun settingsFragment(): SettingsFragment
}