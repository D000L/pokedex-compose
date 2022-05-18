package com.doool.pokedex.presentation.ui.download

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.presentation.Process
import com.doool.pokedex.presentation.utils.goMain

@Composable
fun DownloadScreen(viewModel: DownloadViewModel = hiltViewModel()) {
    val uiState by remember { viewModel.download() }.collectAsState()

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
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
        if (uiState is LoadState.Success) context.goMain()
    }
}
