package com.example.rickandmorty.viewstate

import com.example.rickandmorty.common.AsyncResult
import com.example.rickandmorty.data.settings.AccountSettings
import com.example.rickandmorty.usecase.DarkModeUseCase

data class SettingsViewState(
    val accountSettings: AsyncResult<AccountSettings>? = null,
    val changeDarkMode: DarkModeUseCase.DarkModeChangeState? = null) {

    val isLoading: Boolean
        get() = accountSettings is AsyncResult.Loading || changeDarkMode is DarkModeUseCase.DarkModeChangeState.Loading

    val getAccountSettings
        get() = if (accountSettings is AsyncResult.Success && accountSettings.data != null) {
            accountSettings.data
        } else null

    val changeTheme
        get() = if (changeDarkMode is DarkModeUseCase.DarkModeChangeState.Updated) {
            changeDarkMode.isDarkMode
        } else null
}