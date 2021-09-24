package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemon @Inject constructor(private val pokemonRepository: PokemonRepository) {

//  suspend operator fun invoke(id: Int): Result<PokemonDetail> {
//    return try {
//      Result.success(pokemonRepository.getAllPokemon(id))
//    } catch (e: Throwable) {
//      Result.failure(e)
//    }
//  }
}