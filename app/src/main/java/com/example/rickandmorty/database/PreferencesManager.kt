package com.example.rickandmorty.database

interface LocalPersistenceManager {

    fun setNightMode(value: Boolean)
    fun isNightModeToggled() : Boolean

}