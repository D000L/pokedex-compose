package com.doool.pokedex.data.mapper

import com.doool.pokedex.data.response.ItemResponse
import com.doool.pokedex.domain.model.Item
import com.doool.pokedex.domain.model.LocalizedString

fun ItemResponse.toModel(): Item = with(this) {
  Item(
    id = id,
    name = name,
    names = names.map { LocalizedString(it.name, it.language.name) },
    sprites = sprites.default,
    cost = cost,
    flavorText = flavorTextEntries.map { it.text },
    category = category.toModel(),
    attributes = attributes.map { it.toModel() },
  )
}