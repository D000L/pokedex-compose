package com.doool.pokedex.move.feature.info

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asFlow
import com.doool.pokedex.core.base.BaseViewModel
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.domain.usecase.GetMove
import com.doool.pokedex.move.destination.PARAM_MOVE_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class MoveInfoViewModel @Inject constructor(
    private val getMove: GetMove,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    val move = savedStateHandle.getLiveData<String>(PARAM_MOVE_NAME)
        .asFlow()
        .onStart { delay(100) }
        .flatMapLatest { getMove(it) }
        .filterIsInstance<LoadState.Success<PokemonMove>>().map { it.data }
        .stateInWhileSubscribed()
}
