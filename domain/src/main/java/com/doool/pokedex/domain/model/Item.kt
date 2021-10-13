package com.doool.pokedex.domain.model

data class Item(
  val id: Int = -1,
  val name: String = "",
  val sprites: String = "",
  val cost: Int = 0,
  val category: Info = Info(),
  val flavorText: List<String> = listOf(),
  val attributes: List<Info> = listOf()
)