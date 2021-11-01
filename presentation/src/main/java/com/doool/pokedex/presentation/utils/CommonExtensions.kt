package com.doool.pokedex.presentation.utils

import androidx.compose.runtime.Composable
import com.doool.pokedex.presentation.LoadState

fun String.capitalizeAndRemoveHyphen(): String {
  return split('-').map {
    it.replaceFirstChar { it.uppercaseChar() }
  }.joinToString(" ")
}

inline fun <reified T> List<*>.asType(): List<T>? =
  if (all { it is T }) this as List<T>
  else null