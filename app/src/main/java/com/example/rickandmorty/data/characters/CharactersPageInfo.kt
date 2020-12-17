package com.example.rickandmorty.data.characters

data class CharactersPageInfo (
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String
)