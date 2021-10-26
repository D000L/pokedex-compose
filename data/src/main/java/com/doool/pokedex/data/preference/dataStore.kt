package com.doool.pokedex.data.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.doool.pokedex.domain.model.Language
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingPreferences constructor(context: Context) {
  companion object {
    private val LanguageKey = stringPreferencesKey("language")
  }

  private val appContext = context.applicationContext

  val language: Flow<String>
    get() = appContext.dataStore.data.map { preferences ->
      preferences[LanguageKey] ?: Language.English.name
    }

  suspend fun setLanguage(language: String) {
    appContext.dataStore.edit { preferences ->
      preferences[LanguageKey] = language
    }
  }

  suspend fun clear() {
    appContext.dataStore.edit { preferences ->
      preferences.clear()
    }
  }
}