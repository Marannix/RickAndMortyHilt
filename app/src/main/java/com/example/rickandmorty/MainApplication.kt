package com.example.rickandmorty

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.example.rickandmorty.dagger.components.ApplicationComponent
import com.example.rickandmorty.dagger.components.DaggerApplicationComponent
import com.example.rickandmorty.database.LocalPersistenceManager
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MainApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var sharedPreferences: LocalPersistenceManager

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        initComponent().inject(this)
        sharedPreferences.setNightMode(true)
        if (sharedPreferences.isNightModeToggled()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    private fun initComponent() : ApplicationComponent {
        if (!this::applicationComponent.isInitialized) {
            applicationComponent = createComponent()
        }
        return applicationComponent
    }

    private fun createComponent() : ApplicationComponent {
        return DaggerApplicationComponent.builder().application(this).build()
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

}