package com.doool.pokedex.presentation.ui.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.doool.pokedex.domain.model.Language

@Composable
fun SettingScreen(viewModel: SettingViewModel = hiltViewModel(), finish: () -> Unit) {
  Item("Language") {}

  LanguageDialogHandler(finish) {
    viewModel.updateLanguage(it)
  }
}

@Composable
private fun LanguageDialogHandler(finish: () -> Unit, setLanguage: (Language) -> Unit) {
  Dialog(onDismissRequest = { finish() }) {
    Column {
      Language.values().map {
        Item(it.title) { setLanguage(it) }
      }
    }
  }
}

@Composable
private fun Item(title: String, onClick: () -> Unit) {
  Box(
    modifier = Modifier
      .height(40.dp)
      .fillMaxWidth()
      .clickable { onClick() }
      .padding(horizontal = 20.dp),
  ) {
    Text(modifier = Modifier.align(Alignment.CenterStart), text = title)
  }
}