package com.doool.pokedex.data.response

import androidx.annotation.Keep

@Keep
data class ListItem<T>(
  val count: Int,
  val next: String,
  val previous: String?,
  val results: List<T>
)