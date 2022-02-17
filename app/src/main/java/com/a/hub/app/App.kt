package com.a.hub.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        ThemeHelper.checkTheme(this)
        //initTimber()
    }

    /*private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("TimberInitializer is initialized.")
        }
    }*/
}