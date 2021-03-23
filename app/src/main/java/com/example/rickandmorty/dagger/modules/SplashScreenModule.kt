package com.example.rickandmorty.dagger.modules

import androidx.fragment.app.FragmentActivity
import com.example.rickandmorty.activity.SplashScreenActivity
import dagger.Binds
import dagger.Module

@Module
abstract class SplashScreenModule {

    @Binds
    abstract fun provideSplashScreenActivity(activity: SplashScreenActivity): FragmentActivity

}