package com.example.rickandmorty.data.episodes

import com.google.gson.annotations.SerializedName

data class EpisodesResult(
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