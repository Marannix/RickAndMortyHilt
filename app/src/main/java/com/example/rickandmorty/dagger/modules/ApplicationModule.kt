package com.example.rickandmorty.dagger.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.rickandmorty.MainApplication
import com.example.rickandmorty.database.LocalPersistenceManager
import com.example.rickandmorty.database.LocalPersistenceManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {

    @Provides
    fun provideApplication(@ApplicationContext context: Context) : Context {
        return context
    }

    @Provides
    internal fun provideLocalPersistenceManager(preference: SharedPreferences) : LocalPersistenceManager {
        return LocalPersistenceManagerImpl(preference)
    }
}