package com.doool.pokedex.presentation.ui.download

import androidx.lifecycle.ViewModel
import com.doool.pokedex.domain.usecase.DownloadStaticData
import com.doool.pokedex.domain.usecase.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.scan
import javax.inject.Inject

data class DownloadUIState(
  val loadProgress: Float = 0f,
  val complete: Boolean = false,
  val error: Boolean = false
)

@HiltViewModel
class DownloadViewModel @Inject constructor(
  private val downloadStaticData: DownloadStaticData
) : ViewModel() {

  fun download(): Flow<DownloadUIState> {
    return downloadStaticData().scan(DownloadUIState()) { uiState, state ->
      when (state) {
        is LoadState.Complete -> uiState.copy(complete = true, error = false)
        LoadState.Error -> uiState.copy(complete = false, error = true)
        is LoadState.Loading -> uiState.copy(
          complete = false,
          error = false,
          loadProgress = state.amount
        )
      }
    }
  }
}