package com.doool.pokedex.presentation.ui.download

import com.doool.pokedex.domain.usecase.DownloadStaticData
import com.doool.pokedex.presentation.LoadState
import com.doool.pokedex.presentation.base.BaseViewModel
import com.doool.pokedex.presentation.withLoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DownloadViewModel @Inject constructor(
  private val downloadStaticData: DownloadStaticData
) : BaseViewModel() {

  fun download(): StateFlow<LoadState<Unit>> =
    downloadStaticData().withLoadState().stateInWhileSubscribed { LoadState.Loading }
}