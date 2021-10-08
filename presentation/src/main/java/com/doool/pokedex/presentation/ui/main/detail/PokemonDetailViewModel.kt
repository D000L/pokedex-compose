package com.doool.pokedex.presentation.ui.main.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doool.pokedex.domain.model.*
import com.doool.pokedex.domain.usecase.*
import com.doool.pokedex.presentation.utils.lazyMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailUiState(
  val pokemon: PokemonDetail = PokemonDetail(),
  val species: PokemonSpecies = PokemonSpecies(),
  val damageRelations: List<Damage> = listOf(),
  val evolutionChain: List<PokemonEvolutionChain> = listOf()
)

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
  private val getPokemon: GetPokemon,
  private val getPokemonSpecies: GetPokemonSpecies,
  private val getPokemonEvolutionChain: GetPokemonEvolutionChain,
  private val getDamageRelations: GetDamageRelations,
  private val getPokemonMove: GetPokemonMove
) : ViewModel() {

  private val currentItem = MutableStateFlow(1)

  private val pokemonMap: Map<Int, Flow<PokemonDetail>> = lazyMap { id ->
    return@lazyMap getPokemon(id).stateIn(
      viewModelScope,
      SharingStarted.Lazily,
      PokemonDetail()
    )
  }

  fun setCurrentItem(id: Int) {
    viewModelScope.launch {
      currentItem.emit(id)
    }
  }

  fun getPokemon(): Flow<PokemonDetail> {
    return currentItem.flatMapLatest {
      pokemonMap.getValue(it)
    }
  }

  fun getUiState(): Flow<DetailUiState> {
    return currentItem.flatMapLatest { id ->
      val pokemon = getPokemon(id)
      val species = getPokemonSpecies(id)

      val evolutionChain = species.flatMapLatest {
        getPokemonEvolutionChain(it.evolutionUrl)
      }
      val damageRelations = pokemon.flatMapLatest {
        getDamageRelations(it.types.map { it.name })
      }

      merge(pokemon, species, evolutionChain, damageRelations)
        .scan(DetailUiState()) { state, item ->
          when (item) {
            is PokemonDetail -> state.copy(pokemon = item)
            is PokemonSpecies -> state.copy(species = item)
            is List<*> -> {
              when (item.firstOrNull()) {
                is Damage -> state.copy(damageRelations = item as List<Damage>)
                is PokemonEvolutionChain -> state.copy(evolutionChain = item as List<PokemonEvolutionChain>)
                else -> state
              }
            }
            else -> state
          }
        }
    }
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  fun loadPokemonImage(id: Int): Flow<String> {
    return pokemonMap.getValue(id).map { it.image }
  }

  fun loadPokemonMove(name: String): Flow<PokemonMove> {
    return getPokemonMove(name)
  }
}