package com.projects.android.recipebook.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val themePreferences = ThemePreferences(application)

    val isDarkTheme: LiveData<Boolean> = themePreferences.isDarkTheme.asLiveData()

    fun toggleTheme(isDark: Boolean) {
        viewModelScope.launch {
            themePreferences.saveThemePreference(isDark)
        }
    }
}