package com.doool.pokedex.domain.usecase.search

import com.doool.pokedex.domain.model.Item
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.domain.model.PokemonSpecies
import com.doool.pokedex.domain.repository.PokemonRepository
import com.doool.pokedex.domain.repository.SearchRepository
import com.doool.pokedex.domain.usecase.BaseParamsUseCase
import javax.inject.Inject

data class SearchResult(
  val pokemon: List<Pair<PokemonDetail, PokemonSpecies>>,
  val item: List<Item>,
  val move: List<PokemonMove>
)

class Search @Inject constructor(
  private val searchRepository: SearchRepository,
  private val pokemonRepository: PokemonRepository
) : BaseParamsUseCase<Pair<String, Int>, SearchResult>() {

  override suspend fun execute(params: Pair<String, Int>): SearchResult {
    val pokemon = searchRepository.searchPokemonNames(params.first, params.second)
      .map {
        val pokemon = pokemonRepository.getPokemon(it.name)
        val species = pokemonRepository.getPokemonSpecies(pokemon.species.id)
        Pair(pokemon, species)
      }
    val item = searchRepository.searchItem(params.first, params.second)
      .map { pokemonRepository.getItem(it.name) }
    val move = searchRepository.searchMoveNames(params.first, params.second)
      .map { pokemonRepository.getPokemonMove(it.name) }

    return SearchResult(pokemon, item, move)
  }
}