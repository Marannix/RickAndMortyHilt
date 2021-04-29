package com.example.rickandmorty.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

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

    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}