package com.example.rickandmorty.viewmodel

import com.example.rickandmorty.common.mapToAsyncResult
import com.example.rickandmorty.usecase.DarkModeSettingUseCase
import com.example.rickandmorty.usecase.DarkModeUseCase
import com.example.rickandmorty.usecase.SettingsUseCase
import com.example.rickandmorty.viewstate.SettingsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    settingsUseCase: SettingsUseCase,
    private val darkModeUseCase: DarkModeUseCase,
    private val darkModeSettingUseCase: DarkModeSettingUseCase
):
    RxViewModelStore<SettingsViewState, SettingsViewModel.SettingsViewEvent>(SettingsViewState()) {

    init {
        settingsUseCase.invoke()
            .mapToAsyncResult()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { accountSettings ->
                applyState( Reducer { it.copy(accountSettings = accountSettings) } )
            }.addDisposable()

        darkModeSettingUseCase.invoke()
            .mapToAsyncResult()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { darkMode ->
                applyState( Reducer { it.copy(darkMode = darkMode) } )
            }.addDisposable()
    }

    fun setDarkMode(isDarkMode: Boolean) {
        darkModeUseCase.invoke(isDarkMode)
            .subscribeOn(Schedulers.io())
            .subscribe { state ->
                applyState( Reducer { it.copy(changeDarkMode = state) } )
            }.addDisposable()
    }

    fun changeTheme(isDarkMode: Boolean) {
        publish(SettingsViewEvent.UpdateTheme(isDarkMode))
    }

    sealed class SettingsViewEvent {
        data class GenericErrorEvent(val error: Throwable?) : SettingsViewEvent()
        data class UpdateTheme(val isDarkMode: Boolean) : SettingsViewEvent()
    }
}