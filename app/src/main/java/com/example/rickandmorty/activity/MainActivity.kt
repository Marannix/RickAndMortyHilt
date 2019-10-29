package com.example.rickandmorty.activity

import android.os.Bundle
import com.example.rickandmorty.R
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }
}
