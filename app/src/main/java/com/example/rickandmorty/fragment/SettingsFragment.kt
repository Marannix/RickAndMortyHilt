package com.example.rickandmorty.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.rickandmorty.R
import com.example.rickandmorty.common.AutoCompositeDisposable
import com.example.rickandmorty.common.addTo
import com.example.rickandmorty.viewmodel.SettingsViewModel
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment() {

    interface SettingsInterface {
        fun changeTheme(checked: Boolean)
    }

    private val disposable: AutoCompositeDisposable by lazy { AutoCompositeDisposable(lifecycle) }
    private var listener: SettingsInterface? = null

    private val viewModel: SettingsViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)
            .get(SettingsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.events().subscribe {
            when (it) {
                is SettingsViewModel.SettingsViewEvent.GenericErrorEvent -> {
                    // TODO: Create Error
                }
                is SettingsViewModel.SettingsViewEvent.UpdateTheme -> {
                    listener?.changeTheme(it.isDarkMode)
                }
            }
            //TODO: Create a dialog which says to restart the application
        }.addTo(disposable)

        viewModel.states()
            .distinctUntilChanged()
            .subscribe { state ->

                state.isDarkMode.let {
                    darkModeSwitch.isChecked = it
                }
//                state.getAccountSettings?.let {
//                    darkModeSwitch.isChecked = it.isDarkMode
//                }

                state.changeTheme?.let {
                    viewModel.changeTheme(it)
                }
            }.addTo(disposable)

        darkModeSwitch.setOnClickListener {
            viewModel.setDarkMode(darkModeSwitch.isChecked)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as SettingsInterface
    }

    override fun onDestroy() {
        super.onDestroy()
        listener = null
    }
}