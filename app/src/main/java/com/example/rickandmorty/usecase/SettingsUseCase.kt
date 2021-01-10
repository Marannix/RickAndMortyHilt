package com.example.rickandmorty.usecase

import com.example.rickandmorty.data.settings.AccountSettings
import com.example.rickandmorty.repository.SettingsRepository
import io.reactivex.Observable
import javax.inject.Inject

class SettingsUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    operator fun invoke() : Observable<AccountSettings> {
        return settingsRepository.getSettings()
    }
}