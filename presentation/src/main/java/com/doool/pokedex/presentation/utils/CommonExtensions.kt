package com.doool.pokedex.presentation.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import com.doool.pokedex.domain.LoadState

fun String.capitalizeAndRemoveHyphen(): String {
  return split('-').map {
    it.replaceFirstChar { it.uppercaseChar() }
  }.joinToString(" ")
}

fun LazyListState.getItemTopOffset(index: Int = 0): Int {
  val topOffset = layoutInfo.visibleItemsInfo.getOrNull(index)?.offset ?: 0
  val startOffset = layoutInfo.viewportStartOffset
  return topOffset - startOffset
}

@Composable
fun <T : Any> LoadState<T>.Process(
  onError: @Composable () -> Unit = {},
  onLoading: @Composable () -> Unit = {},
  onComplete: @Composable (T) -> Unit = {}
) {
  when (this) {
    is LoadState.Success -> onComplete(data)
    LoadState.Error -> onError()
    is LoadState.Loading -> onLoading()
  }
}

inline fun <reified T> List<*>.asType(): List<T>? =
  if (all { it is T }) this as List<T>
  else null