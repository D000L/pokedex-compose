package com.doool.pokedex.presentation.ui.main.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class Menu {
  Pokemon, Games, Move, Item, Berry, Location
}

@Composable
fun MenuScreen(onClickMenu: (Menu) -> Unit) {
  Column {
    Menu.values().forEach {
      MenuItem(it, onClickMenu)
    }
  }
}

@Composable
private fun MenuItem(menu: Menu, onClickMenu: (Menu) -> Unit) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(40.dp)
      .clickable {
        onClickMenu(menu)
      },
    contentAlignment = Alignment.Center
  ) {
    Text(text = menu.name)
  }
}