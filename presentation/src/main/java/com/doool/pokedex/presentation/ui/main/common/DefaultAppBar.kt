package com.doool.pokedex.presentation.ui.main.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.doool.pokedex.presentation.ui.LocalNavController
import com.doool.pokedex.presentation.utils.getItemTopOffset

val TOOLBAR_HEIGHT = 56.dp

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.listAppBar(
  state: LazyListState,
  title: String = "",
  tintColor: Color = Color.Black
) {
  stickyHeader {
    val density = LocalDensity.current
    val showHeaderDivider by derivedStateOf { state.getItemTopOffset(1) < density.run { TOOLBAR_HEIGHT.toPx() } }
    DefaultAppBar(title = title, tintColor = tintColor, showDivider = showHeaderDivider)
  }
}

@Composable
fun DefaultAppBar(title: String = "", tintColor: Color = Color.Black, showDivider: Boolean = true) {
  val navController = LocalNavController.current

  val modifier = if (showDivider) Modifier
    .shadow(8.dp)
    .background(Color.White) else Modifier

  Box(
    modifier = modifier
      .height(TOOLBAR_HEIGHT)
      .fillMaxWidth()
  ) {
    IconButton(
      modifier = Modifier.align(Alignment.CenterStart),
      onClick = navController::navigateUp
    ) {
      Icon(Icons.Default.ArrowBack, contentDescription = null, tint = tintColor)
    }
    Text(
      modifier = Modifier.align(Alignment.Center),
      text = title,
      style = MaterialTheme.typography.h4
    )
    if (showDivider) {
      Divider(modifier = Modifier.align(Alignment.BottomCenter), thickness = 0.5.dp)
    }
  }
}