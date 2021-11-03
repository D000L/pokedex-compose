package com.doool.pokedex.presentation.ui.download

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.presentation.Process

@Composable
fun DownloadScreen(viewModel: DownloadViewModel = hiltViewModel(), completeDownload: () -> Unit) {

  val uiState by remember { viewModel.download() }.collectAsState()

  Column(
    Modifier.fillMaxSize(1f),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    uiState.Process(onError = {
      Text(text = "Fail Download")
    }, onLoading = {
      CircularProgressIndicator()
    })
  }

  LaunchedEffect(uiState) {
    if (uiState is LoadState.Success) completeDownload()
  }
}