package com.doool.pokedex.data.mapper

import com.doool.pokedex.data.response.FormResponse
import com.doool.pokedex.domain.model.Form
import com.doool.pokedex.domain.model.LocalizedString

fun FormResponse.toModel(): Form = with(this) {
  Form(
    id = id,
    name = name,
    formName = formName,
    formNames = formNames.map { LocalizedString(it.name, it.language.name) },
    formOrder = formOrder,
    isBattleOnly = isBattleOnly,
    isDefault = isDefault,
    types = types.map { it.toModel() },
    pokemon = pokemon.toModel(),
    isMega = isMega,
    order = order
  )
}