package com.example.rickandmorty.fragment

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

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