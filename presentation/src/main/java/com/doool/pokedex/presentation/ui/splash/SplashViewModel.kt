package com.doool.pokedex.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doool.pokedex.domain.usecase.CheckIsDownloaded
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val checkIsDownloaded: CheckIsDownloaded) :
  ViewModel() {

  var isReady: Boolean = false
  var isDownloaded: Boolean = false

  init {
    viewModelScope.launch {
      checkIsDownloaded().collectLatest {
        isDownloaded = it
        isReady = true
      }
    }
  }
}