package com.example.rickandmorty.data.episodes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmorty.data.network.EpisodeResponse
import io.reactivex.Single

@Dao
interface EpisodesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertEpisodes(episodes: EpisodeResponse)

    @Query("select * from episodes where characterId = :character_id order by id")
    fun getEpisodes(character_id: String): Single<List<EpisodeResponse>>

    @Query("select * from episodes")
    fun getAllEpisodes(): List<EpisodeResponse>

    @Query("delete from episodes")
    fun flushEpisodeData()
}