package com.doool.pokedex.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doool.pokedex.domain.usecase.CheckIsDownloaded
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val checkIsDownloaded: CheckIsDownloaded) :
  ViewModel() {

  var isReady = MutableStateFlow(false)
  var isDownloaded = MutableStateFlow(false)

  init {
    viewModelScope.launch {
      delay(1000)
      checkIsDownloaded().collectLatest {
        isReady.emit(true)
        isDownloaded.emit(it)
      }
    }
  }
}