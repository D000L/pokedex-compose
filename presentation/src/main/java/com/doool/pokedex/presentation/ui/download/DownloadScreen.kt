package com.doool.pokedex.presentation.ui.download

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DownloadScreen(viewModel: DownloadViewModel = hiltViewModel(), completeDownload: () -> Unit) {

  val uiState by remember { viewModel.download() }.collectAsState(initial = DownloadUIState())

  Column(
    Modifier.fillMaxSize(1f),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    if (!uiState.error) {
      LinearProgressIndicator(progress = uiState.loadProgress)
    } else {
      Text(text = "Fail Download")
    }
  }

  LaunchedEffect(uiState.complete) {
    if (uiState.complete) completeDownload()
  }
}