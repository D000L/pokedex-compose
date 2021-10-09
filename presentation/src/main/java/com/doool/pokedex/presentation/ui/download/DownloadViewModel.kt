package com.doool.pokedex.presentation.ui.download

import androidx.lifecycle.ViewModel
import com.doool.pokedex.domain.usecase.DownloadStaticData
import com.doool.pokedex.domain.usecase.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DownloadViewModel @Inject constructor(
  private val downloadStaticData: DownloadStaticData
) : ViewModel() {

  fun download(): Flow<LoadState<Unit>> = downloadStaticData()
}