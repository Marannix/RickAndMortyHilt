package com.example.rickandmorty.database

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.rickandmorty.database.LocalPersistenceManagerImpl.Constants.KEY_FIRST_LAUNCH
import com.example.rickandmorty.database.LocalPersistenceManagerImpl.Constants.KEY_NIGHT_MODE
import javax.inject.Inject

class LocalPersistenceManagerImpl @Inject constructor(private val preference: SharedPreferences) :
    LocalPersistenceManager {

    override fun setNightMode(value: Boolean) {
        preference.edit { putBoolean(KEY_NIGHT_MODE, value) }
    }

    override fun isNightModeToggled() = preference.getBoolean(KEY_NIGHT_MODE, false)

    override fun setFirstLaunch(value: Boolean) {
        preference.edit { putBoolean(KEY_FIRST_LAUNCH, value) }
    }

    override fun isFirstLaunch() = preference.getBoolean(KEY_FIRST_LAUNCH, true)

    object Constants {
        const val KEY_NIGHT_MODE = "KEY_NIGHT_MODE"
        const val KEY_FIRST_LAUNCH = "KEY_FIRST_LAUNCH"
    }

}