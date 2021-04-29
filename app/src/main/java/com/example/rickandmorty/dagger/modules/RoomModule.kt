package com.example.rickandmorty.dagger.modules

import android.app.Application
import androidx.room.Room
import com.example.rickandmorty.data.characters.CharactersDao
import com.example.rickandmorty.data.episodes.EpisodesDao
import com.example.rickandmorty.data.favourites.FavouriteDao
import com.example.rickandmorty.data.settings.SettingsDao
import com.example.rickandmorty.database.ApplicationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(application: Application): ApplicationDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            ApplicationDatabase::class.java, "rickandmorty.db"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideCharactersDao(applicationDatabase: ApplicationDatabase): CharactersDao {
        return applicationDatabase.charactersDao()
    }

    @Singleton
    @Provides
    fun providesEpisodeDao(applicationDatabase: ApplicationDatabase): EpisodesDao {
        return applicationDatabase.episodeDao()
    }

    @Singleton
    @Provides
    fun providesFavouriteDao(applicationDatabase: ApplicationDatabase): FavouriteDao {
        return applicationDatabase.favouriteDao()
    }

    @Singleton
    @Provides
    fun providesSettingsDao(applicationDatabase: ApplicationDatabase): SettingsDao {
        return applicationDatabase.settingsDao()
    }
}