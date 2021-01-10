package com.example.rickandmorty.data.episodes

data class EpisodePageInfo (
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
