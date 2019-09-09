package com.example.rickandmorty.data.characters

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharactersResults (
    val id: String,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String,
    val location: CharacterLocation,
    val episode: List<String>
) : Parcelable