package com.doool.pokedex.domain.repository

import com.doool.pokedex.domain.model.Language
import com.doool.pokedex.domain.model.PokedexSetting
import kotlinx.coroutines.flow.Flow

interface SettingRepository {

  fun getSetting() : Flow<PokedexSetting>
  suspend fun setLanguage(language: Language)

}