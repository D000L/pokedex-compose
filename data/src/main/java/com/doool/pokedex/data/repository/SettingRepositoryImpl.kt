package com.doool.pokedex.data.repository

import com.doool.pokedex.data.preference.SettingPreferences
import com.doool.pokedex.domain.model.Language
import com.doool.pokedex.domain.model.PokedexSetting
import com.doool.pokedex.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(private val settingPreferences: SettingPreferences) :
  SettingRepository {

  override fun getSetting(): Flow<PokedexSetting> = settingPreferences.language.map {
    PokedexSetting(Language.valueOf(it))
  }

  override suspend fun setLanguage(language: Language) {
    settingPreferences.setLanguage(language.name)
  }
}