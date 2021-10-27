package com.doool.pokedex.presentation.ui.main.setting

import androidx.lifecycle.viewModelScope
import com.doool.pokedex.presentation.Language
import com.doool.pokedex.domain.repository.SettingRepository
import com.doool.pokedex.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val settingRepository: SettingRepository) :
  BaseViewModel() {

  val language = settingRepository.getLanguageCode().map { Language.fromCode(it) }
    .stateIn(viewModelScope, SharingStarted.Eagerly, Language.English)

  fun updateLanguage(language: Language) {
    viewModelScope.launch {
      settingRepository.saveLanguageCode(language.code)
    }
  }
}