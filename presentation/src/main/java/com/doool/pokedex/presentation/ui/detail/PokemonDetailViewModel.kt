package com.doool.pokedex.presentation.ui.detail

import android.util.Log
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

  fun getDetail(id: Int) = flow {
    getPokemon(id).fold({
      emit(it)
    }, {})
  }

  fun getSpecies(id: Int) = flow {
    getPokemonSpecies(id).fold({
      emit(it)
    }, {})
  }

  fun getEvolutionChain(url: String) = flow {
    val evolutionId = url.removePrefix("https://pokeapi.co/api/v2/evolution-chain/").removeSuffix("/")
      .toInt()
    getPokemonEvolutionChain(evolutionId).fold({
      emit(it)
    }, {})
  }

  fun <K, V> lazyMap(initializer: (K) -> V): Map<K, V> {
    val map = mutableMapOf<K, V>()
    return map.withDefault { key ->
      val newValue = initializer(key)
      map[key] = newValue
      return@withDefault newValue
    }
  }

  private val uiState: Map<Int, Flow<DetailUiState>> = lazyMap { id ->
    var uiState = DetailUiState()
    Log.d("sdfsadf", "uiState $id")

    return@lazyMap merge(
      getDetail(id).map {
        Log.d("sdfsadf", "getDetail $it")
        uiState = uiState.copy(pokemon = it)
        uiState
      }.catch { emit(uiState.copy(pokemon = PokemonDetail(id = -1))) },
      getSpecies(id).map {
        Log.d("sdfsadf", "getSpecies $it")
        uiState = uiState.copy(species = it)
        uiState
      }.catch { emit(uiState.copy(pokemon = PokemonDetail(id = -2))) }
    ).stateIn(viewModelScope, SharingStarted.Lazily, DetailUiState())
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  fun loadPokemon(id: Int): Flow<DetailUiState> {
    Log.d("sdfsadf", "load $id")
    return uiState.getValue(id)
  }
}