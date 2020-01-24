package com.example.rickandmorty.data.episodes

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "episode")
data class EpisodesResult(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    @SerializedName("air_Date")
    val airdate: String,
    @SerializedName("characters")
    val charactersUrls: List<String>,
    @SerializedName("url")
    val episodeUrl: String,
    val created: String
)