package com.example.rickandmorty.fragment

import android.os.Bundle
import android.util.Log
import dagger.android.support.DaggerFragment
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins

abstract class BaseFragment : DaggerFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleUndeliverableException()
    }

    private fun handleUndeliverableException() {
        //Added due to -  2 exceptions occurred. which causes a crash
        RxJavaPlugins.setErrorHandler { e ->
            var error = ""
            if (e is UndeliverableException) {
                error = e.cause.toString()
            }
            Log.e("Whats going on", error)
        }
    }
}