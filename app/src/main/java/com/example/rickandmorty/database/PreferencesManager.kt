package com.example.rickandmorty.database

interface LocalPersistenceManager {

    fun setNightMode(value: Boolean)
    fun isNightModeToggled() : Boolean
    fun setFirstLaunch(value: Boolean)
    fun isFirstLaunch() : Boolean

}