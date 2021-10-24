package com.doool.pokedex.presentation.ui.main.pokemon.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.model.*
import com.doool.pokedex.domain.usecase.*
import com.doool.pokedex.domain.withLoadState
import com.doool.pokedex.presentation.base.BaseViewModel
import com.doool.pokedex.presentation.utils.lazyMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PokemonInfoViewModel @Inject constructor(
  private val getPokemonUsecase: GetPokemon,
  private val getPokemonSpecies: GetPokemonSpecies,
  private val getPokemonEvolutionChain: GetPokemonEvolutionChain,
  private val getDamageRelations: GetDamageRelations,
  private val getPokemonNames: GetPokemonNames,
  private val getMove: GetMove,
  savedStateHandle: SavedStateHandle
) : BaseViewModel() {

  private val _currentPokemon = savedStateHandle.getLiveData<String>(NAME_PARAM)
  private val currentPokemon = _currentPokemon.asFlow().distinctUntilChanged()

  var initIndex = 0

  val pokemonList = flow {
    val indexed = getPokemonNames()
    if (_currentPokemon.value.isNullOrEmpty()) _currentPokemon.value = indexed.first().name
    initIndex = indexed.indexOfFirst { it.name == _currentPokemon.value }
    emit(indexed)
  }

  val pokemon = currentPokemon.flatMapLatest { getPokemonUsecase(it) }.stateInWhileSubscribed()
  val species = currentPokemon.flatMapLatest { getPokemonSpecies(it) }.stateInWhileSubscribed()

  val evolutionChain = species.filter { it.evolutionUrl.isNotBlank() }
    .flatMapLatest { getPokemonEvolutionChain(it.evolutionUrl) }
    .stateInWhileSubscribed { emptyList() }

  val damageRelations = pokemon.map { it.types.map { it.name } }
    .flatMapLatest { getDamageRelations(it) }
    .stateInWhileSubscribed { emptyList() }

  fun setCurrentPokemon(name: String) {
    viewModelScope.launch {
      _currentPokemon.postValue(name)
    }
  }

  fun loadPokemonMove(name: String) =
    getMove(name).withLoadState().stateInWhileSubscribed(1000) { LoadState.Loading }
}