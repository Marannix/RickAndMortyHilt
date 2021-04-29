package com.example.rickandmorty.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.rickandmorty.database.LocalPersistenceManager
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}