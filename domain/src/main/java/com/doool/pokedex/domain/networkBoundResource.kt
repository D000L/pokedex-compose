package com.doool.pokedex.domain

import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

@WorkerThread
fun <R : Any> networkBoundResources(
  query: () -> Flow<List<R>>,
  fetch: suspend (List<R>) -> Unit,
  shouldFetch: (R) -> Boolean = { true }
) = flow {
  emit(LoadState.Loading())

  try {
    val data = query().first()
    val fetchList = data.filter { shouldFetch(it) }

    if (fetchList.isNotEmpty()) {
      emit(LoadState.Loading(data.size))
      fetch(fetchList)

      emitAll(query().map {
        LoadState.Success(it)
      })
    } else {
      LoadState.Success(data)
    }
  } catch (e: Throwable) {
    emit(LoadState.Error)
  }
}.flowOn(Dispatchers.IO)

@WorkerThread
fun <R : Any> networkBoundResource(
  query: suspend () -> R,
  fetch: suspend (R) -> Unit,
  shouldFetch: (R) -> Boolean = { true }
) = flow {
  emit(LoadState.Loading())

  try {
    val data = query()
    if (shouldFetch(data)) {
      fetch(data)

      emit(LoadState.Success(query()))
    } else {
      emit(LoadState.Success(data))
    }
  } catch (e: Throwable) {
    emit(LoadState.Error)
  }
}.flowOn(Dispatchers.IO)