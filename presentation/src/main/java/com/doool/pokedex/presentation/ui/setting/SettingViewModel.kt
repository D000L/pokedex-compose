package com.doool.pokedex.presentation.ui.setting

import androidx.lifecycle.viewModelScope
import com.doool.pokedex.domain.model.Language
import com.doool.pokedex.domain.repository.SettingRepository
import com.doool.pokedex.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val settingRepository: SettingRepository) :
  BaseViewModel() {

  val setting = settingRepository.getSetting()

  fun updateLanguage(language: Language) {
    viewModelScope.launch {
      settingRepository.setLanguage(language)
    }
  }
}