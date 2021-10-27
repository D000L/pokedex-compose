package com.doool.pokedex.presentation.ui.move_info

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asFlow
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.domain.usecase.GetMove
import com.doool.pokedex.domain.withLoadState
import com.doool.pokedex.presentation.base.BaseViewModel
import com.doool.pokedex.presentation.ui.move_info.destination.NAME_PARAM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class MoveInfoViewModel @Inject constructor(
  private val getMove: GetMove,
  private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

  val move =
    savedStateHandle.getLiveData<String>(NAME_PARAM).asFlow().flatMapLatest {
      getMove(it).withLoadState()
    }.stateInWhileSubscribed { LoadState.Loading }
}