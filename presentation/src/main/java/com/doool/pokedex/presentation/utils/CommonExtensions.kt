package com.doool.pokedex.presentation.utils

import androidx.compose.foundation.lazy.LazyListState

fun String.capitalizeAndRemoveHyphen(): String {
  return split('-').map {
    it.replaceFirstChar { it.uppercaseChar() }
  }.joinToString(" ")
}

fun LazyListState.getItemTopOffset(index : Int = 0): Int {
  val topOffset = layoutInfo.visibleItemsInfo.getOrNull(index)?.offset ?: 0
  val startOffset = layoutInfo.viewportStartOffset
  return topOffset - startOffset
}