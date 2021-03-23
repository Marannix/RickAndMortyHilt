package com.example.rickandmorty.dagger.modules

import dagger.Module

@Module (includes = [MainActivityBindingModule::class, SplashScreenBindingModule::class])
abstract class ActivityBuilder