package com.example.rickandmorty.data.network

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "episodes")
data class EpisodeResponse(
    @PrimaryKey(autoGenerate = true)
    val episodeId: Int,
    var characterId: String,
    val id: String,
    val name: String,
    @SerializedName("air_date")
    val airdate: String,
    val episode: String
)