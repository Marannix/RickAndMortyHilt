package com.example.rickandmorty.data.settings

import androidx.room.*
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*

@Dao
interface SettingsDao {
    @Query("UPDATE settings SET isDarkMode = :isDarkMode WHERE id = 1")
    fun updateDarkMode(isDarkMode: Boolean)

    @Transaction
    @Query("select * from settings WHERE id = 1")
    fun getSettings() : Observable<AccountSettings>

    //TODO: Better name is required
    @Query("select * from settings")
    fun getSettingsAtLaunch() : AccountSettings

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun setSettings(settings: AccountSettings)

}