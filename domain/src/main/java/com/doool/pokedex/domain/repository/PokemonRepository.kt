package com.doool.pokedex.domain.repository

import com.doool.pokedex.domain.model.Pokemon

interface PokemonRepository {

  suspend fun getPokemon(id : Int): Pokemon
}