package com.doool.pokedex.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingRepository {

  fun getLanguageCode(): Flow<String?>

  suspend fun saveLanguageCode(code: String)

}