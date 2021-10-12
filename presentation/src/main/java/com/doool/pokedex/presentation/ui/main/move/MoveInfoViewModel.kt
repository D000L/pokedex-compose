package com.doool.pokedex.presentation.ui.main.move

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.domain.usecase.GetMove
import com.doool.pokedex.domain.usecase.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class MoveInfoViewModel @Inject constructor(
  private val getMove: GetMove,
  private val savedStateHandle: SavedStateHandle
) : ViewModel() {

  val move: Flow<LoadState<PokemonMove>> =
    savedStateHandle.getLiveData<String>(NAME_PARAM).asFlow().flatMapLatest {
      getMove(it)
    }
}