package com.example.rickandmorty.data.characters

import com.google.gson.annotations.SerializedName

data class CharactersResponse (
    @SerializedName("info")
    val characterPageInfo: CharactersPageInfo,
    @SerializedName("results")
    val charactersResults : List<CharactersResults>
)