package com.a.hub.app

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.BuildCompat
import androidx.preference.PreferenceManager

object ThemeHelper {

    const val THEME_KEY = "appTheme"

    const val DEFAULT_MODE = "default"
    const val LIGHT_MODE = "light"
    const val DARK_MODE = "dark"

    const val DEFAULT = LIGHT_MODE

    fun checkTheme(context: Context) {
        applyTheme(getMode(context))
    }

    fun switchTheme(context: Context, isChecked: Boolean) {
        val sharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        if (isChecked) {
            sharedPreferences.edit().putString(THEME_KEY, DARK_MODE).apply()
            applyTheme(DARK_MODE)
        } else {
            sharedPreferences.edit().putString(THEME_KEY, LIGHT_MODE).apply()
            applyTheme(LIGHT_MODE)
        }
    }

    fun applyTheme(themePref: String) {
        when (themePref) {
            LIGHT_MODE -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            DARK_MODE -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else -> {
                if (BuildCompat.isAtLeastQ()) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                }
            }
        }
    }

    fun isDarkMode(context: Context): Boolean {
        return getMode(context) == DARK_MODE
    }

    private fun getMode(context: Context): String {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString(THEME_KEY, DEFAULT) ?: DEFAULT
    }
}