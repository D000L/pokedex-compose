package com.doool.pokedex.presentation.ui.main

import androidx.lifecycle.viewModelScope
import com.doool.pokedex.domain.repository.SettingRepository
import com.doool.pokedex.domain.usecase.CheckIsDownloaded
import com.doool.pokedex.presentation.Language
import com.doool.pokedex.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  checkIsDownloaded: CheckIsDownloaded,
  settingRepository: SettingRepository
) : BaseViewModel() {

  var isReady: Boolean = false
  var needDownload = checkIsDownloaded().onCompletion {
    isReady = true
  }.filter { !it }

  val language = settingRepository.getLanguageCode().map { Language.fromCode(it) }
    .stateIn(viewModelScope, SharingStarted.Eagerly, Language.English)
}