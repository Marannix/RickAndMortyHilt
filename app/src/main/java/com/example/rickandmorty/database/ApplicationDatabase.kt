package com.example.rickandmorty.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rickandmorty.data.Converters
import com.example.rickandmorty.data.characters.CharactersDao
import com.example.rickandmorty.data.characters.CharactersResults

@Database(
    entities = [CharactersResults::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun charactersDao(): CharactersDao

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