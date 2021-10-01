package com.doool.pokedex.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.PokemonEvolutionChain
import com.doool.pokedex.domain.model.PokemonSpecies
import com.doool.pokedex.domain.usecase.GetPokemon
import com.doool.pokedex.domain.usecase.GetPokemonEvolutionChain
import com.doool.pokedex.domain.usecase.GetPokemonSpecies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class DetailUiState(
  val pokemon: PokemonDetail = PokemonDetail(),
  val species: PokemonSpecies = PokemonSpecies(),
  val evolutionChain: List<PokemonEvolutionChain> = listOf()
)

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
  private val getPokemon: GetPokemon,
  private val getPokemonSpecies: GetPokemonSpecies,
  private val getPokemonEvolutionChain: GetPokemonEvolutionChain
) : ViewModel() {

  private fun parseEvolutionChainId(url: String): Int {
    return url.removePrefix("https://pokeapi.co/api/v2/evolution-chain/").removeSuffix("/")
      .toInt()
  }

  private fun <K, V> lazyMap(initializer: (K) -> V): Map<K, V> {
    val map = mutableMapOf<K, V>()
    return map.withDefault { key ->
      return@withDefault map.getOrPut(key) { initializer(key) }
    }
  }

  private val uiState: Map<Int, Flow<DetailUiState>> = lazyMap { id ->
    val pokemon = getPokemon(id)
    val species = getPokemonSpecies(id)
    val evolutionChain = species.flatMapLatest {
      getPokemonEvolutionChain(parseEvolutionChainId(it.evolutionUrl))
    }

    return@lazyMap merge(pokemon, species, evolutionChain)
      .scan(DetailUiState()) { state, item ->
        when (item) {
          is PokemonDetail -> state.copy(pokemon = item)
          is PokemonSpecies -> state.copy(species = item)
          is List<*> -> state.copy(evolutionChain = item as List<PokemonEvolutionChain>)
          else -> state
        }
      }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        DetailUiState()
      )
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  fun loadPokemon(id: Int): Flow<DetailUiState> {
    return uiState.getValue(id)
  }
}