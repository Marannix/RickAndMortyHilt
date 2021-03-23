package com.example.rickandmorty.dagger.modules

import com.example.rickandmorty.activity.SplashScreenActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SplashScreenBindingModule {
    @ContributesAndroidInjector(modules = [SplashScreenModule::class])
    abstract fun contributeSplashScreenActivity(): SplashScreenActivity
}