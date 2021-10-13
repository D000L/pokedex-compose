package com.doool.pokedex.presentation.ui.download

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doool.pokedex.domain.usecase.DownloadStaticData
import com.doool.pokedex.domain.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DownloadViewModel @Inject constructor(
  private val downloadStaticData: DownloadStaticData
) : ViewModel() {

  fun download(): StateFlow<LoadState<Unit>> = downloadStaticData().stateIn(viewModelScope,
    SharingStarted.WhileSubscribed(), LoadState.Loading())
}