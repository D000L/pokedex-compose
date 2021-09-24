package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonList @Inject constructor(private val pokemonRepository: PokemonRepository) {

  operator fun invoke(): Flow<List<PokemonDetail>> = pokemonRepository.getAllPokemon()
}