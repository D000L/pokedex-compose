package com.doool.pokedex.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.PokemonEvolutionChain
import com.doool.pokedex.domain.model.PokemonSpecies
import com.doool.pokedex.domain.usecase.GetPokemon
import com.doool.pokedex.domain.usecase.GetPokemonEvolutionChain
import com.doool.pokedex.domain.usecase.GetPokemonSpecies
import com.doool.pokedex.presentation.utils.lazyMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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

  private val currentItem = MutableStateFlow(1)


  private val pokemonMap: Map<Int, Flow<PokemonDetail>> = lazyMap { id ->
    return@lazyMap getPokemon(id).stateIn(
      viewModelScope,
      SharingStarted.Lazily,
      PokemonDetail()
    )
  }

  private val uiState: Map<Int, Flow<DetailUiState>> = lazyMap { id ->
    val pokemon = pokemonMap.getValue(id)
    val species = getPokemonSpecies(id)
    val evolutionChain = species.flatMapLatest {
      getPokemonEvolutionChain(it.evolutionUrl)
    }

    return@lazyMap merge(pokemon, species, evolutionChain)
      .scan(DetailUiState()) { state, item ->
        when (item) {
          is PokemonDetail -> state.copy(pokemon = item)
          is PokemonSpecies -> state.copy(species = item)
          is List<*> -> state.copy(evolutionChain = item as List<PokemonEvolutionChain>)
          else -> state
        }
      }
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
    return currentItem.flatMapLatest {
      uiState.getValue(it)
    }
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  fun loadPokemonImage(id: Int): Flow<String> {
    return pokemonMap.getValue(id).map { it.image }
  }
}