package com.example.rickandmorty.dagger.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.rickandmorty.database.LocalPersistenceManager
import com.example.rickandmorty.database.LocalPersistenceManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    internal fun provideContext(application: Application): Context {
        return application.baseContext
    }

    @Provides
    internal fun provideLocalPersistenceManager(preference: SharedPreferences) : LocalPersistenceManager {
        return LocalPersistenceManagerImpl(preference)
    }
}