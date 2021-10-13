package com.doool.pokedex.presentation.ui.main.pokemon.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.doool.pokedex.domain.LoadState
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
class PokemonInfoViewModel @Inject constructor(
  private val getPokemonUsecase: GetPokemon,
  private val getPokemonSpecies: GetPokemonSpecies,
  private val getPokemonEvolutionChain: GetPokemonEvolutionChain,
  private val getDamageRelations: GetDamageRelations,
  private val getMove: GetMove,
  private val getPokemonNames: GetPokemonNames,
  private val savedStateHandle: SavedStateHandle
) : ViewModel() {

  private val _currentItem = savedStateHandle.getLiveData<String>(NAME_PARAM)
  private val currentItem = _currentItem.asFlow()

  val pokemonList = flow {
    val names = getPokemonNames()
    _currentItem.postValue(names.first())
    emit(names)
  }

  var initIndex = 0

  init {
    viewModelScope.launch {
      initIndex = pokemonList.single().indexOf(_currentItem.value)
    }
  }

  private val pokemonMap: Map<String, Flow<PokemonDetail>> = lazyMap { name ->
    return@lazyMap getPokemonUsecase(name).filterIsInstance<LoadState.Success<PokemonDetail>>()
      .map {
        it.data
      }
  }

  fun setCurrentItem(name: String) {
    viewModelScope.launch {
      _currentItem.postValue(name)
    }
  }

  fun getPokemon(): Flow<PokemonDetail> {
    return currentItem.flatMapLatest {
      pokemonMap.getValue(it)
    }
  }

  fun getUiState(): Flow<DetailUiState> {
    return currentItem.flatMapLatest { name ->
      val pokemon = pokemonMap.getValue(name)
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
    return getMove(name).filterIsInstance<LoadState.Success<PokemonMove>>().map {
      it.data
    }
  }
}