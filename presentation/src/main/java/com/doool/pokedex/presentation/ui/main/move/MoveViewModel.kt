package com.doool.pokedex.presentation.ui.main.move

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.domain.usecase.GetMove
import com.doool.pokedex.domain.usecase.GetMoveNames
import com.doool.pokedex.domain.withLoadState
import com.doool.pokedex.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class MoveViewModel @Inject constructor(
  private val getMoveNames: GetMoveNames,
  private val getMoveUsecase: GetMove,
  private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

  private var searchQuery: String? = savedStateHandle.get<String?>(QUERY_PARAM)

  val moveList = flow {
    emit(getMoveNames(searchQuery))
  }

  fun getMove(name: String) =
    getMoveUsecase(name).withLoadState().stateInWhileSubscribed(1000) { LoadState.Loading }
}
