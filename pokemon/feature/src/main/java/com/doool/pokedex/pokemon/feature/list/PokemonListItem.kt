package com.doool.pokedex.pokemon.feature.list

import com.doool.pokedex.domain.model.Info
import com.doool.pokedex.domain.model.LocalizedString

data class PokemonListItem(
    val id: Int,
    val name: String,
    val names: List<LocalizedString> = emptyList(),
    val formNames: List<LocalizedString> = emptyList(),
    val image: String,
    val types: List<Info> = emptyList()
)
