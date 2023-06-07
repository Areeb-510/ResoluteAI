package com.example.resoluteai

import android.app.Application
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Disable dark mode for the entire app
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Optionally, you can also update the application's configuration to reflect the new mode
        val configuration = Configuration(resources.configuration)
        configuration.uiMode = Configuration.UI_MODE_NIGHT_NO
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}