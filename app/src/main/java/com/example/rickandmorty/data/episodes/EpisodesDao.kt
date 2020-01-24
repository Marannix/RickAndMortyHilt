package com.example.rickandmorty.data.episodes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmorty.data.characters.CharacterEpisodeResponse
import io.reactivex.Single

@Dao
interface EpisodesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertEpisodes(episodes: CharacterEpisodeResponse)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllEpisodes(episodes: CharacterEpisodeResponse)

    @Query("select * from episodes where characterId = :character_id order by id")
    fun getEpisodes(character_id: String): Single<List<CharacterEpisodeResponse>>

    @Query("select * from episodes")
    fun getAllEpisodes(): Single<List<CharacterEpisodeResponse>>

    @Query("delete from episodes")
    fun flushEpisodeData()
}