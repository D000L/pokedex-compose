package com.doool.pokedex.domain.repository

import com.doool.pokedex.domain.model.Pokemon
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.PokemonSpecies

interface PokemonRepository {

  suspend fun getPokemonList(offset: Int, countPerPage: Int = 20): List<Pokemon>

  suspend fun getPokemonDetail(id: Int): PokemonDetail
  suspend fun getPokemonSpecies(id: Int): PokemonSpecies
}