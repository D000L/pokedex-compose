package com.doool.pokedex.presentation.ui.pokemon_info

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.model.Form
import com.doool.pokedex.domain.model.LocalizedString
import com.doool.pokedex.domain.usecase.GetAbility
import com.doool.pokedex.domain.usecase.GetDamageRelations
import com.doool.pokedex.domain.usecase.GetForm
import com.doool.pokedex.domain.usecase.GetMove
import com.doool.pokedex.domain.usecase.GetPokemon
import com.doool.pokedex.domain.usecase.GetPokemonEvolutionChain
import com.doool.pokedex.domain.usecase.GetPokemonList
import com.doool.pokedex.domain.usecase.GetPokemonSpecies
import com.doool.pokedex.presentation.base.BaseViewModel
import com.doool.pokedex.presentation.combineLoadState
import com.doool.pokedex.presentation.flatMapLatestState
import com.doool.pokedex.presentation.mapData
import com.doool.pokedex.presentation.scanLoadState
import com.doool.pokedex.presentation.ui.pokemon_info.destination.NAME_PARAM
import com.doool.pokedex.presentation.ui.pokemon_info.model.AboutUIModel
import com.doool.pokedex.presentation.ui.pokemon_info.model.EvolutionListUIModel
import com.doool.pokedex.presentation.ui.pokemon_info.model.HeaderUIModel
import com.doool.pokedex.presentation.ui.pokemon_info.model.MoveListUIModel
import com.doool.pokedex.presentation.ui.pokemon_info.model.StatsUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

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
        if (_currentPokemon.value.isNullOrEmpty()) _currentPokemon.value = info.firstOrNull()?.name
        initIndex = Math.max(0, info.indexOfFirst { it.name == _currentPokemon.value })
        emit(info)
    }

    private val pokemon = currentPokemon.flatMapLatest { getPokemonUsecase(GetPokemon.Params.ByName(it)) }
    private val species = pokemon.flatMapLatestState { getPokemonSpecies(it.species.id) }

    val headerState = combineLoadState(pokemon, species)
        .scanLoadState { (pokemon, species) ->
            val form = pokemon.name.contains("-")
            var formNames = emptyList<LocalizedString>()

            if (form) formNames = getForm(pokemon.name).filterIsInstance<LoadState.Success<Form>>()
                .firstOrNull()?.data?.formNames ?: emptyList()

            HeaderUIModel(
                id = pokemon.id,
                name = pokemon.name,
                names = species.names,
                types = pokemon.types,
                formNames = formNames
            )
        }.stateInWhileLazily { LoadState.loading() }

    val aboutUIState = combineLoadState(pokemon, species).flatMapLatestState { (pokemon, species) ->
        combineLoadState(pokemon.abilities.map { getAbility(it.ability.name) }).mapData {
            Triple(pokemon, species, it)
        }
    }.scanLoadState { (pokemon, species, abilities) ->
        AboutUIModel(
            descriptions = species.flavorText,
            height = pokemon.height,
            weight = pokemon.weight,
            abilities = abilities,
            genera = species.genera,
            maleRate = species.maleRate,
            femaleRate = species.femaleRate,
            isGenderless = species.isGenderless,
            eggGroups = species.eggGroups
        )
    }.stateInWhileLazily { LoadState.loading() }

    val statsUIState = pokemon.flatMapLatestState { pokemon ->
        getDamageRelations(pokemon.types.map { it.name }).mapData {
            Pair(pokemon, it)
        }
    }.scanLoadState { (pokemon, damageRelations) ->
        StatsUIModel(stats = pokemon.stats, damageRelations = damageRelations)
    }.stateInWhileLazily { LoadState.loading() }

    val moveUIState = pokemon.scanLoadState { pokemon ->
        MoveListUIModel(moves = pokemon.moves)
    }.stateInWhileLazily { LoadState.loading() }

    val evolutionListUIState = species.flatMapLatestState {
        getPokemonEvolutionChain(it.evolutionUrl)
    }.scanLoadState { evolutionChain ->
        EvolutionListUIModel(evolutions = evolutionChain)
    }.stateInWhileLazily { LoadState.loading() }

    fun setCurrentPokemon(name: String) {
        viewModelScope.launch {
            _currentPokemon.postValue(name)
        }
    }

    fun loadPokemonMove(name: String) =
        getMove(name).onStart { delay(500) }.stateInWhileSubscribed(1000) { LoadState.Loading() }
}