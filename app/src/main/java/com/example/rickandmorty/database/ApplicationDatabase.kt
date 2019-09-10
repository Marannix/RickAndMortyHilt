package com.example.rickandmorty.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rickandmorty.data.Converters
import com.example.rickandmorty.data.characters.CharactersDao
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.data.episodes.EpisodesDao
import com.example.rickandmorty.data.network.EpisodeResponse

@Database(
    entities = [CharactersResults::class, EpisodeResponse::class],
    version = 4
)
@TypeConverters(Converters::class)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun charactersDao(): CharactersDao
    abstract fun episodeDao(): EpisodesDao

    object DatabaseProvider {
        private var instance: ApplicationDatabase? = null

        fun getInstance(context: Context): ApplicationDatabase? {
            if (instance == null) {
                synchronized(ApplicationDatabase::class) {
                    instance = buildDatabase(context)
                }
            }
            return instance
        }

        private fun buildDatabase(context: Context): ApplicationDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                ApplicationDatabase::class.java, "rickandmorty.db"
            ).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }

    }
}