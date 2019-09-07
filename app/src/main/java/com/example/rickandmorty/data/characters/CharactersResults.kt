package com.example.rickandmorty.data.characters

data class CharactersResults (
    val id: String,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String,
    val location: CharacterLocation
)