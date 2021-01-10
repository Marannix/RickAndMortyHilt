package com.example.rickandmorty.repository

import com.example.rickandmorty.data.settings.AccountSettings
import com.example.rickandmorty.data.settings.SettingsDao
import com.example.rickandmorty.database.LocalPersistenceManagerImpl
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class SettingsRepository @Inject constructor(private val persistenceManager: LocalPersistenceManagerImpl,
private val settingsDao: SettingsDao) {

    fun getSettings() : Observable<AccountSettings> {
       // return Observable.just(AccountSettings(settingsDao.getSettings()))
        return settingsDao.getSettings()
    }

    fun setDarkMode(isDarkMode: Boolean) : Completable {
        return Completable.fromCallable {
            settingsDao.updateDarkMode(isDarkMode)
            persistenceManager.setNightMode(isDarkMode)
        }
    }
}