package com.example.rickandmorty.data.settings

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "settings")
data class AccountSettings(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val isDarkMode: Boolean
) : Parcelable