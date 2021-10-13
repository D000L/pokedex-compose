package com.doool.pokedex.data.mapper

import com.doool.pokedex.data.entity.ItemEntity
import com.doool.pokedex.data.response.ItemResponse
import com.doool.pokedex.data.toResponse
import com.doool.pokedex.domain.model.Item

fun ItemEntity.toModel(): Item = with(this) {
  json?.toResponse<ItemResponse>()?.toModel() ?: Item(name = name)
}

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