package com.example.rickandmorty.data.characters

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterLocation(
    val name: String
) : Parcelable