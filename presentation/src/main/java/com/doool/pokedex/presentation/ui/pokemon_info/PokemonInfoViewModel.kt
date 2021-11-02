package com.doool.pokedex.presentation.ui.pokemon_info

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.doool.pokedex.domain.model.LocalizedString
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.usecase.*
import com.doool.pokedex.presentation.LoadState
import com.doool.pokedex.presentation.base.BaseViewModel
import com.doool.pokedex.presentation.ui.pokemon_info.destination.NAME_PARAM
import com.doool.pokedex.presentation.ui.pokemon_info.model.*
import com.doool.pokedex.presentation.withLoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PokemonInfoViewModel @Inject constructor(
  private val getPokemonUsecase: GetPokemon,
  private val getPokemonSpecies: GetPokemonSpecies,
  private val getForm: GetForm,
  private val getPokemonEvolutionChain: GetPokemonEvolutionChain,
  private val getDamageRelations: GetDamageRelations,
  private val getPokemonList: GetPokemonList,
  private val getAbility: GetAbility,
  private val getMove: GetMove,
  savedStateHandle: SavedStateHandle
) : BaseViewModel() {

  private val _currentPokemon = savedStateHandle.getLiveData<String>(NAME_PARAM)
  val currentPokemon = _currentPokemon.asFlow().distinctUntilChanged()

  var initIndex = 0

  val pokemonList = flow {
    val info = getPokemonList()
    if (_currentPokemon.value.isNullOrEmpty()) _currentPokemon.value = info.first().name
    initIndex = info.indexOfFirst { it.name == _currentPokemon.value }
    emit(info)
  }

  private val pokemon = currentPokemon.flatMapLatest { getPokemonUsecase(it).withLoadState() }
  private val species = pokemon.filterIsInstance<LoadState.Success<PokemonDetail>>()
    .flatMapLatest { getPokemonSpecies(it.data.species.id).withLoadState() }

  val headerState = combine(
    pokemon,
    species,
    ::Pair
  ).scan(HeaderUIModel()) { state, (pokemon, species) ->
    if (pokemon is LoadState.Success && species is LoadState.Success) {
      val form = pokemon.data.name.contains("-")
      var formNames = emptyList<LocalizedString>()
      if (form) formNames = getForm(pokemon.data.name).first().formNames
      state.copy(
        isLoading = false,
        id = pokemon.data.id,
        name = pokemon.data.name,
        names = species.data.names,
        types = pokemon.data.types,
        formNames = formNames
      )
    } else state.copy(isLoading = true)
  }.stateInWhileSubscribed()

  val aboutState = combine(
    pokemon,
    species,
    ::Pair
  ).scan(AboutUIModel()) { state, (pokemon, species) ->
    if (pokemon is LoadState.Success && species is LoadState.Success) {
      val abilities = pokemon.data.abilities.map { getAbility(it.ability.name).first() }
      state.copy(
        isLoading = false,
        descriptions = species.data.flavorText,
        height = pokemon.data.height,
        weight = pokemon.data.weight,
        abilities = abilities,
        genera = species.data.genera,
        maleRate = species.data.maleRate,
        femaleRate = species.data.femaleRate,
        eggGroups = species.data.eggGroups
      )
    } else state.copy(isLoading = true)
  }.stateInWhileSubscribed()

  val statsState =
    combine(pokemon, species, ::Pair).scan(StatsUIModel()) { state, (pokemon, species) ->
      if (pokemon is LoadState.Success && species is LoadState.Success) {
        val damageRelations = getDamageRelations(pokemon.data.types.map { it.name }).first()
        state.copy(isLoading = false, stats = pokemon.data.stats, damageRelations = damageRelations)
      } else state.copy(isLoading = true)
    }.stateInWhileSubscribed()

  val moveState = pokemon.scan(MoveListUIModel()) { state, pokemon ->
    if (pokemon is LoadState.Success) {
      state.copy(isLoading = false, moves = pokemon.data.moves)
    } else state.copy(isLoading = true)
  }.stateInWhileSubscribed()

  val evolutionChainState = species.scan(EvolutionListUIModel()) { state, species ->
    if (species is LoadState.Success) {
      val evolutionChain = getPokemonEvolutionChain(species.data.evolutionUrl).first()
      state.copy(isLoading = false, evolutions = evolutionChain)
    } else state.copy(isLoading = true)
  }.stateInWhileSubscribed()

  fun setCurrentPokemon(name: String) {
    viewModelScope.launch {
      _currentPokemon.postValue(name)
    }
  }

  fun loadPokemonMove(name: String) =
    getMove(name).onStart { delay(500) }.withLoadState()
      .stateInWhileSubscribed(1000) { LoadState.Loading }
}