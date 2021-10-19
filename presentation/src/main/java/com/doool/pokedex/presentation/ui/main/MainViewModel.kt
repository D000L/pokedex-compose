package com.doool.pokedex.presentation.ui.main

import androidx.lifecycle.ViewModel
import com.doool.pokedex.domain.usecase.CheckIsDownloaded
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(checkIsDownloaded: CheckIsDownloaded) :
  ViewModel() {

  var isReady: Boolean = false
  var needDownload = checkIsDownloaded().onCompletion {
    isReady = true
  }.filter { !it }
}