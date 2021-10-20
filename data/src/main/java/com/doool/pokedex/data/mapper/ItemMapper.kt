package com.doool.pokedex.data.mapper

import com.doool.pokedex.data.response.ItemResponse
import com.doool.pokedex.domain.model.Item

fun ItemResponse.toModel(): Item = with(this) {
  Item(
    id = id,
    name = name,
    sprites = sprites.default,
    cost = cost,
    flavorText = flavorTextEntries.map { it.text },
    category = category.toModel(),
    attributes = attributes.map { it.toModel() },
  )
}