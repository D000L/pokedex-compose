package com.doool.pokedex.presentation.ui.main.move

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.domain.usecase.GetMove
import com.doool.pokedex.domain.usecase.GetMoveNames
import com.doool.pokedex.domain.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MoveViewModel @Inject constructor(
  private val getMoveNames: GetMoveNames,
  private val getMoveUsecase: GetMove,
  private val savedStateHandle: SavedStateHandle
) : ViewModel() {

  private var searchQuery: String? = savedStateHandle.get<String?>(QUERY_PARAM)

  val moveList = flow {
    emit(getMoveNames(searchQuery))
  }

  fun getMove(name: String): Flow<PokemonMove> {
    return getMoveUsecase(name).filterIsInstance<LoadState.Success<PokemonMove>>().map {
      it.data
    }
  }
}
