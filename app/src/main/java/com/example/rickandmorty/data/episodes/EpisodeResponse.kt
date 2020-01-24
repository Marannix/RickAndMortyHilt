package com.example.rickandmorty.data.episodes

import com.google.gson.annotations.SerializedName

data class EpisodeResponse(
    @SerializedName("info")
    val episodesPageInfo: EpisodePageInfo,
    @SerializedName("results")
    val episodes: List<EpisodesResult>
)