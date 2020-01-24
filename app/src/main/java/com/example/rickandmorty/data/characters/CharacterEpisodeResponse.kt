package com.example.rickandmorty.data.characters

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "characterEpisodes")
data class CharacterEpisodeResponse(
    @NonNull
    @PrimaryKey
    val id: String,
    var characterId: String,
    val name: String,
    @SerializedName("air_date")
    val airdate: String,
    val episode: String
)