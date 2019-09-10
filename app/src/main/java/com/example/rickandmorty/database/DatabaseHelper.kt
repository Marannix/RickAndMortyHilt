package com.example.rickandmorty.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.data.network.EpisodeResponse

class DatabaseHelper(private val context: Context) {

    private var database: ApplicationDatabase = ApplicationDatabase.DatabaseProvider.getInstance(context)!!

    fun retrieveDatabase() {
        database = ApplicationDatabase.DatabaseProvider.getInstance(context)!!
    }

    fun getCharacters(): List<CharactersResults> {
        return database.charactersDao().getAllCharacters()
    }

    fun insertCharacters(characters: List<CharactersResults>) {
        database.charactersDao().insertCharacters(characters)
    }

    fun getEpisodes(charactersId: String): List<EpisodeResponse> {
        return database.episodeDao().getEpisodes(charactersId)
    }

    fun insertEpisodes(episode: EpisodeResponse) {
        database.episodeDao().insertEpisodes(episode)
    }
}