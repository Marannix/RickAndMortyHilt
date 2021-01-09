package com.example.rickandmorty.database

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.rickandmorty.database.LocalPersistenceManagerImpl.Constants.KEY_NIGHT_MODE
import javax.inject.Inject

class LocalPersistenceManagerImpl @Inject constructor(private val preference: SharedPreferences) :
    LocalPersistenceManager {

    override fun setNightMode(value: Boolean) {
        preference.edit { putBoolean(KEY_NIGHT_MODE, value) }
    }

    override fun isNightModeToggled() = preference.getBoolean(KEY_NIGHT_MODE, false)

    object Constants {
        const val KEY_NIGHT_MODE = "KEY_NIGHT_MODE"
    }

}