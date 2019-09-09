package com.example.rickandmorty.data.network

import com.google.gson.annotations.SerializedName

data class EpisodeResponse(
    val id: String,
    val name: String,
    @SerializedName("air_date")
    val airdate: String,
    val episode: String
)