package com.example.rickandmorty.data.characters

import com.example.rickandmorty.data.characters.CharactersPageInfo
import com.example.rickandmorty.data.characters.CharactersResults
import com.google.gson.annotations.SerializedName

data class CharactersResponse (
    @SerializedName("info")
    val characterPageInfo: CharactersPageInfo,
    @SerializedName("results")
    val charactersResults : List<CharactersResults>
)