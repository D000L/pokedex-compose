package com.doool.pokedex.presentation.ui.pokemon_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.doool.pokedex.domain.model.LocalizedString
import com.doool.pokedex.domain.model.Pokemon
import com.doool.pokedex.domain.usecase.GetForm
import com.doool.pokedex.domain.usecase.GetPokemon
import com.doool.pokedex.domain.usecase.GetPokemonList
import com.doool.pokedex.domain.usecase.GetPokemonSpecies
import com.doool.pokedex.presentation.LoadState
import com.doool.pokedex.presentation.base.BaseViewModel
import com.doool.pokedex.presentation.ui.pokemon_list.destination.QUERY_PARAM
import com.doool.pokedex.presentation.withLoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
  private val getPokemonList: GetPokemonList,
  private val getPokemonUsecase: GetPokemon,
  private val getPokemonSpecies: GetPokemonSpecies,
  private val getForm: GetForm,
  savedStateHandle: SavedStateHandle
) : BaseViewModel() {

  private var searchQuery: String? = savedStateHandle.get<String?>(QUERY_PARAM)

  val pokemonList = savedStateHandle.getLiveData<List<Pokemon>?>("POKEMON_LIST")

  fun loadPokemonList() {
    viewModelScope.launch {
      pokemonList.postValue(getPokemonList(searchQuery))
    }
  }

  fun getItemState(name: String) = getPokemonUsecase(name).map { pokemon ->
    val species = getPokemonSpecies(pokemon.species.id).first()

    val form = pokemon.name.contains("-")
    var formNames = emptyList<LocalizedString>()
    if (form) formNames = getForm(pokemon.name).first().formNames
    PokemonListItem(
      pokemon.id,
      pokemon.name,
      species.names,
      formNames,
      pokemon.image,
      pokemon.types
    )
  }.onStart { delay(500) }.withLoadState().stateInWhileSubscribed(1000) { LoadState.Loading }
}