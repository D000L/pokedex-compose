package com.doool.pokedex.presentation.ui.main

import androidx.lifecycle.viewModelScope
import com.doool.pokedex.core.Language
import com.doool.pokedex.core.base.BaseViewModel
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.repository.SettingRepository
import com.doool.pokedex.domain.usecase.CheckIsDownloaded
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    checkIsDownloaded: CheckIsDownloaded,
    settingRepository: SettingRepository
) : BaseViewModel() {

    var isReady: Boolean = false

    var needDownload = checkIsDownloaded().onCompletion {
        isReady = true
    }.filter {
        it is LoadState.Success && !it.data
    }

    val language = settingRepository.getLanguageCode().map { Language.fromCode(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, Language.English)
}
