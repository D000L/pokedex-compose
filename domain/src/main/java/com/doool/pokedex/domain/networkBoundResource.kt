package com.doool.pokedex.domain

import androidx.annotation.WorkerThread
import com.doool.pokedex.domain.model.Placeholdable
import com.doool.pokedex.domain.usecase.LoadState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

@WorkerThread
fun <R : Placeholdable> networkBoundResources(
  query: () -> Flow<List<R>>,
  fetch: suspend (List<R>) -> Unit,
  shouldFetch: (R) -> Boolean = { true }
) = flow {
  emit(LoadState.Loading)

  try {
    val data = query().first()
    emit(LoadState.Success(data))

    val fetchList = data.filter { shouldFetch(it) }

    if (fetchList.isNotEmpty()) {
      fetch(fetchList)

      emitAll(query().map {
        LoadState.Success(it)
      })
    }
  } catch (e: Throwable) {
    emit(LoadState.Error)
  }
}.flowOn(Dispatchers.IO)

@WorkerThread
fun <R : Placeholdable> networkBoundResource(
  query: suspend () -> R,
  fetch: suspend (R) -> Unit,
  shouldFetch: (R) -> Boolean = { true }
) = flow {
  emit(LoadState.Loading)

  try {
    val data = query()
    emit(LoadState.Success(data))

    if (shouldFetch(data)) {
      fetch(data)

      emit(LoadState.Success(query()))
    }
  } catch (e: Throwable) {
    emit(LoadState.Error)
  }
}.flowOn(Dispatchers.IO)