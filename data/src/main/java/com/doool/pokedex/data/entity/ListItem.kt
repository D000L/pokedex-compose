package com.doool.pokedex.data.entity

data class ListItem<T>(
  val count: Int,
  val next: String,
  val previous: String?,
  val results: List<T>
)