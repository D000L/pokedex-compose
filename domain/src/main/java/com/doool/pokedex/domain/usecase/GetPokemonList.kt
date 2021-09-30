package com.doool.pokedex.domain.usecase

import androidx.paging.PagingSource
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonList @Inject constructor(private val pokemonRepository: PokemonRepository) {

  operator fun invoke(query: String? = null): PagingSource<Int, PokemonDetail> =
    pokemonRepository.searchPokemonList(query).asPagingSourceFactory()()
}