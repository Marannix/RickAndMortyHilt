package com.example.rickandmorty.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.rickandmorty.R

class SplashScreenActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity().javaClass))
            finish()
        }, 4000)
    }
}