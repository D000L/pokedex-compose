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
  private val getPokemonMove: GetPokemonMove,
  private val getPokemonNames: GetPokemonNames
) : ViewModel() {

  private val currentItem = MutableStateFlow("")

  private val pokemonMap: Map<String, Flow<PokemonDetail>> = lazyMap { name ->
    return@lazyMap getPokemon(name).stateIn(
      viewModelScope,
      SharingStarted.Lazily,
      PokemonDetail()
    )
  }

  val pokemonList = flow {
    val names = getPokemonNames()
    currentItem.emit(names.first())
    emit(names)
  }

  fun setCurrentItem(name: String) {
    viewModelScope.launch {
      currentItem.emit(name)
    }
  }

  fun getPokemon(): Flow<PokemonDetail> {
    return currentItem.flatMapLatest {
      pokemonMap.getValue(it)
    }
  }

  fun getUiState(): Flow<DetailUiState> {
    return currentItem.flatMapLatest { name ->
      val pokemon = getPokemon(name)
      val species = getPokemonSpecies(name)

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
  fun loadPokemonImage(name: String): Flow<String> {
    return pokemonMap.getValue(name).map { it.image }
  }

  fun loadPokemonMove(name: String): Flow<PokemonMove> {
    return getPokemonMove(name)
  }
}