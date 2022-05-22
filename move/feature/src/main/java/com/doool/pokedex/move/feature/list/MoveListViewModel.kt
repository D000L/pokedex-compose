package com.doool.pokedex.move.feature.list

import androidx.lifecycle.SavedStateHandle
import com.doool.pokedex.core.base.BaseViewModel
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.usecase.GetMove
import com.doool.pokedex.domain.usecase.GetMoveNames
import com.doool.pokedex.move.destination.QUERY_PARAM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class MoveListViewModel @Inject constructor(
    private val getMoveNames: GetMoveNames,
    private val getMoveUsecase: GetMove,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private var searchQuery: String? = savedStateHandle[QUERY_PARAM]

    val moveList = flow {
        emit(getMoveNames(searchQuery))
    }

    fun getMove(name: String) = getMoveUsecase(name)
        .onStart { delay(500) }
        .stateInWhileSubscribed(delay = 1000) { LoadState.Loading() }
}
