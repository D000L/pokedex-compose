package com.doool.pokedex.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

open class BaseViewModel : ViewModel() {

  inline fun <reified T> Flow<T>.stateInWhileSubscribed(factory: () -> T? = { null }) =
    this.stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(), factory() ?: T::class.java.newInstance()
    )
}