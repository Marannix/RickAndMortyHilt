package com.example.rickandmorty.usecase

import androidx.room.Update
import com.example.rickandmorty.repository.SettingsRepository
import com.example.rickandmorty.viewmodel.SettingsViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class DarkModeUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    operator fun invoke(isDarkMode: Boolean) : Observable<DarkModeChangeState> {
        return settingsRepository.setDarkMode(isDarkMode)
            .andThen(Single.just<DarkModeChangeState>(DarkModeChangeState.Updated(isDarkMode)))
            .toObservable()
            .onErrorReturn { e -> DarkModeChangeState.Error(e) }
    }

    sealed class DarkModeChangeState {
        object Loading : DarkModeChangeState()
        data class Updated(val isDarkMode: Boolean) : DarkModeChangeState()
        data class Error(val error: Throwable?) : DarkModeChangeState()
    }
}