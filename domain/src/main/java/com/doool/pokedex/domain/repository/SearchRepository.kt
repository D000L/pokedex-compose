package com.doool.pokedex.domain.repository

import com.doool.pokedex.domain.model.PokemonMove

interface SearchRepository {

  suspend fun searchMove(query: String? = null): List<PokemonMove>
}