package com.example.rickandmorty

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.rickandmorty.data.settings.AccountSettings
import com.example.rickandmorty.data.settings.SettingsDao
import com.example.rickandmorty.database.LocalPersistenceManager
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {

    @Inject
    lateinit var sharedPreferences: LocalPersistenceManager

    @Inject
    lateinit var settingsDao: SettingsDao

    override fun onCreate() {
        super.onCreate()
        if (sharedPreferences.isFirstLaunch()) {
            //TODO: Remove darkmode from settings?
            settingsDao.setSettings(AccountSettings(id = 1, isDarkMode = false))
            sharedPreferences.setFirstLaunch(false)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        if (sharedPreferences.isNightModeToggled()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}