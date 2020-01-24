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
    fun insertCharacterEpisodes(episodes: CharacterEpisodeResponse)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllEpisodes(episodes: List<EpisodesResult>)

    @Query("select * from characterEpisodes where characterId = :character_id order by id")
    fun getEpisodes(character_id: String): Single<List<CharacterEpisodeResponse>>

    @Query("select * from episode")
    fun getAllEpisodes(): Single<List<EpisodesResult>>

    @Query("delete from characterEpisodes")
    fun flushCharactersEpisodeData()
}