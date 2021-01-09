package com.example.rickandmorty.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    var hasInitializedRootView = false
    private var rootView: View? = null

    fun getPersistentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?, layout: Int): View? {
        if (rootView == null) {
            // Inflate the layout for this fragment
            rootView = inflater?.inflate(layout,container,false)
        } else {
            // Do not inflate the layout again.
            // The returned View of onCreateView will be added into the fragment.
            // However it is not allowed to be added twice even if the parent is same.
            // So we must remove rootView from the existing parent view group
            // (it will be added back).
            (rootView?.getParent() as? ViewGroup)?.removeView(rootView)
        }

        return rootView
    }
}