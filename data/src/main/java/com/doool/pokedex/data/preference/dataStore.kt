package com.doool.pokedex.data.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingPreferences constructor(context: Context) {
    companion object {
        private val LanguageCodeKey = stringPreferencesKey("language_code")
    }

    private val appContext = context.applicationContext

    val languageCode: Flow<String?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[LanguageCodeKey]
        }

    suspend fun saveLanguageCode(language: String) {
        appContext.dataStore.edit { preferences ->
            preferences[LanguageCodeKey] = language
        }
    }
}