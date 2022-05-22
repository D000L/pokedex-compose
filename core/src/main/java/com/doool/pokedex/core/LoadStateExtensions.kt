package com.doool.pokedex.core

import androidx.compose.runtime.Composable
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.getData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.scan

@Composable
fun <T : Any> LoadState<T>.Process(
    onError: @Composable () -> Unit = {},
    onLoading: @Composable () -> Unit = {},
    onComplete: @Composable (T) -> Unit = {}
) {
    when (this) {
        is LoadState.Success -> onComplete(data)
        is LoadState.Error -> onError()
        is LoadState.Loading -> onLoading()
    }
}

suspend fun <T, R> LoadState<T>.mapData(
    previous: LoadState<R>? = null,
    block: suspend (T) -> R
): LoadState<R> {
    return when (this) {
        is LoadState.Error -> LoadState.failure(e)
        is LoadState.Loading -> LoadState.loading(previous?.getData())
        is LoadState.Success -> LoadState.success(block(data))
    }
}

fun <T, R> Flow<LoadState<T>>.mapData(previous: LoadState<R>? = null, block: suspend (T) -> R) =
    map { it.mapData(previous, block) }

fun <T, R> Flow<LoadState<T>>.flatMapLatestState(
    block: suspend (T) -> Flow<LoadState<R>>
): Flow<LoadState<R>> = flatMapLatest {
    when (it) {
        is LoadState.Error -> flowOf(LoadState.failure(it.e))
        is LoadState.Loading -> flowOf(LoadState.loading())
        is LoadState.Success -> block(it.data)
    }
}

fun <T, R> Flow<LoadState<T>>.scanLoadState(block: suspend (T) -> R) =
    scan(LoadState.loading<R>()) { state, data ->
        data.mapData(state) { block(it) }
    }

fun <T1, T2> combineLoadState(
    flow: Flow<LoadState<T1>>,
    flow2: Flow<LoadState<T2>>
): Flow<LoadState<Pair<T1, T2>>> = combine(flow, flow2) { data1, data2 ->
    if (data1 is LoadState.Success && data2 is LoadState.Success) {
        LoadState.success(Pair(data1.data, data2.data))
    } else if (data1 is LoadState.Error || data2 is LoadState.Error) {
        val error = (data1 as? LoadState.Error)?.e ?: (data2 as? LoadState.Error)?.e
        LoadState.failure(error)
    } else {
        LoadState.loading()
    }
}

inline fun <reified T> combineLoadState(
    flows: Iterable<Flow<LoadState<T>>>
): Flow<LoadState<List<T>>> = combine(flows) {
    val successList = it.filterIsInstance<LoadState.Success<T>>()
    if (it.size == successList.size) {
        return@combine LoadState.success(successList.map { it.data })
    }

    val hasError = it.filterIsInstance<LoadState.Error<T>>()
    if (hasError.isNotEmpty()) {
        return@combine LoadState.failure(hasError.first().e)
    }

    return@combine LoadState.loading()
}
