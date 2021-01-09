package com.example.rickandmorty.viewmodel

import com.example.rickandmorty.viewstate.SettingsViewState
import javax.inject.Inject

class SettingsViewModel @Inject constructor(): RxViewModelStore<SettingsViewState, SettingsViewModel.SettingsViewEvent>(SettingsViewState()) {

    sealed class SettingsViewEvent {
        data class GenericErrorEvent(val error: Throwable?) : SettingsViewEvent()
    }
}