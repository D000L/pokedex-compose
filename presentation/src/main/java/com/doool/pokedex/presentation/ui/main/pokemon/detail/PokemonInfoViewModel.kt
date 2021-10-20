package com.doool.pokedex.presentation.ui.main.pokemon.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.model.*
import com.doool.pokedex.domain.usecase.*
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
  private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

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

  val pokemon = currentItem.flatMapLatest { getPokemonUsecase(it) }.stateInWhileSubscribed()
  val species = currentItem.flatMapLatest { getPokemonSpecies(it) }.stateInWhileSubscribed()

  val evolutionChain = species.filter { it.evolutionUrl.isNotBlank() }
    .flatMapLatest { getPokemonEvolutionChain(it.evolutionUrl) }
    .stateInWhileSubscribed { emptyList() }

  val damageRelations = pokemon.flatMapLatest { getDamageRelations(it.types.map { it.name }) }
    .stateInWhileSubscribed { emptyList() }

  private val pokemonMap: Map<String, Flow<PokemonDetail>> = lazyMap { name ->
    return@lazyMap getPokemonUsecase(name)
  }

  fun setCurrentPokemon(name: String) {
    viewModelScope.launch {
      _currentItem.postValue(name)
    }
  }

  val pokemonImageMap: Map<String, Flow<String>> = lazyMap { name ->
    return@lazyMap pokemonMap.getValue(name).map { it.image }
  }
}