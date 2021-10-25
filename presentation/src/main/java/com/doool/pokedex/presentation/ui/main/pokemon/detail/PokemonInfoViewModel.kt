package com.doool.pokedex.presentation.ui.main.pokemon.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.usecase.*
import com.doool.pokedex.domain.withLoadState
import com.doool.pokedex.presentation.base.BaseViewModel
import com.doool.pokedex.presentation.utils.flowCycleLogging
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
  private val getAbility: GetAbility,
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

  private val pokemon =
    currentPokemon.flatMapLatest { getPokemonUsecase(it) }.stateInWhileSubscribed()
  private val species =
    currentPokemon.flatMapLatest { getPokemonSpecies(it) }.stateInWhileSubscribed()

  val headerState = combine(pokemon, species) { pokemon, species ->
    InfoHeaderItem(pokemon.id, pokemon.name, species.names, pokemon.image, pokemon.types)
  }.stateInWhileSubscribed()

  val aboutState = combine(pokemon, species) { pokemon, species ->
    val abilities = pokemon.abilities.map { getAbility(it.ability.name).first() }
    InfoAboutItem(
      species.flavorText,
      pokemon.height,
      pokemon.weight,
      abilities,
      species.genera,
      species.maleRate,
      species.femaleRate,
      species.eggGroups
    )
  }.withLoadState().stateInWhileLazily { LoadState.Loading }

  val statsState = combine(pokemon, species) { pokemon, species ->
    val damageRelations = getDamageRelations(pokemon.types.map { it.name }).first()
    InfoStatsItem(pokemon.stats, damageRelations)
  }.withLoadState().stateInWhileLazily { LoadState.Loading }

  val moveState = pokemon.map { it.moves }.stateInWhileLazily { emptyList() }

  val evolutionChain = species.filter { it.evolutionUrl.isNotBlank() }
    .flatMapLatest { getPokemonEvolutionChain(it.evolutionUrl) }
    .stateInWhileLazily { emptyList() }

  fun setCurrentPokemon(name: String) {
    viewModelScope.launch {
      _currentPokemon.postValue(name)
    }
  }

  fun loadPokemonMove(name: String) =
    getMove(name).withLoadState().stateInWhileSubscribed(1000) { LoadState.Loading }
}