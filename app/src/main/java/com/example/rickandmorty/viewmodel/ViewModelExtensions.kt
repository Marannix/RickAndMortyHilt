@file:Suppress("UNCHECKED_CAST")

package com.example.rickandmorty.viewmodel

import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * View models extensions helpers for ViewModel factory creation with args support
 * on top of fragments ktx viewModels<T>
 * or
 * on top of activities ktx activityViewModels<T>
 *
 * Ref:
 * https://developer.android.com/kotlin/ktx
 * https://youtu.be/9fn5s8_CYJI?t=1889
 */

inline fun <reified T : ViewModel> FragmentActivity.viewModel(
        crossinline provider: () -> T
) = viewModels<T> {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>) = provider() as T
    }
}

inline fun <reified T : ViewModel> Fragment.viewModel(
        crossinline provider: () -> T
) = viewModels<T> {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>) = provider() as T
    }
}

inline fun <reified T : ViewModel> Fragment.activityViewModel(
        crossinline provider: () -> T
) = activityViewModels<T> {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>) = provider() as T
    }
}