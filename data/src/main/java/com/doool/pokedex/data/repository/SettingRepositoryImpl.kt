package com.doool.pokedex.data.repository

import com.doool.pokedex.data.preference.SettingPreferences
import com.doool.pokedex.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(private val settingPreferences: SettingPreferences) :
    SettingRepository {

    override fun getLanguageCode(): Flow<String?> = settingPreferences.languageCode

    override suspend fun saveLanguageCode(code: String) {
        settingPreferences.saveLanguageCode(code)
    }
}
