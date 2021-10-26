package com.doool.pokedex.presentation.ui.main.move

import androidx.lifecycle.SavedStateHandle
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.usecase.GetMove
import com.doool.pokedex.domain.usecase.GetMoveNames
import com.doool.pokedex.domain.withLoadState
import com.doool.pokedex.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
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

  fun getMove(name: String) = getMoveUsecase(name).onStart { delay(400) }.withLoadState()
    .stateInWhileSubscribed(1000) { LoadState.Loading }
}
