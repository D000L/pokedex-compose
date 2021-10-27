package com.doool.pokedex.presentation.ui.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.doool.pokedex.presentation.Language
import com.doool.pokedex.presentation.ui.widget.Space

@Composable
fun SettingDropDown(expended: Boolean, dismiss: () -> Unit) {
  val viewModel: SettingViewModel = hiltViewModel()

  DropdownMenu(expanded = expended, onDismissRequest = { dismiss() }) {
    val currentLanguage by viewModel.language.collectAsState()

    Language.values().forEach {
      val color = if (currentLanguage == it) Color.Gray else Color.White
      DropdownMenuItem(modifier = Modifier.background(color), onClick = {
        viewModel.updateLanguage(it)
        dismiss()
      }) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Image(
            modifier = Modifier.size(32.dp),
            painter = painterResource(id = it.flagResId),
            contentDescription = null
          )
          Space(12.dp)
          Text(text = it.title)
        }
      }
    }
  }
}