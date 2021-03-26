package com.example.rickandmorty.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.rickandmorty.R
import com.example.rickandmorty.database.LocalPersistenceManager
import kotlinx.android.synthetic.main.activity_splash_screen.*
import javax.inject.Inject

class SplashScreenActivity : BaseActivity() {

    @Inject
    lateinit var sharedPreferences: LocalPersistenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        sharedPreferences.isNightModeToggled().let { isDarkMode ->
            if (isDarkMode) {
                splashScreenAnimation.setAnimation(this.getString(R.string.splash_screen_loading_light))
            } else {
                splashScreenAnimation.setAnimation(this.getString(R.string.splash_screen_loading))
            }
        }

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity().javaClass))
            finish()
        }, 4000)
    }
}