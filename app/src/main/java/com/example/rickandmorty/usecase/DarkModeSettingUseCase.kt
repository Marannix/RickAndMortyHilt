package com.example.rickandmorty.usecase

import com.example.rickandmorty.repository.SettingsRepository
import io.reactivex.Observable
import javax.inject.Inject

class DarkModeSettingUseCase @Inject constructor(val repository: SettingsRepository){

    operator fun invoke() : Observable<Boolean> {
        return repository.isDarkMode()
    }
}