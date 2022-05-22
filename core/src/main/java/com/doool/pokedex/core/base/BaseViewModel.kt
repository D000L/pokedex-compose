package com.doool.pokedex.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

open class BaseViewModel : ViewModel() {

    inline fun <reified T> Flow<T>.stateInWhileSubscribed(
        delay: Long = 0,
        initFactory: () -> T? = { null }
    ) =
        this.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(delay), initFactory() ?: T::class.java.newInstance()
        )

    inline fun <reified T> Flow<T>.stateInWhileLazily(factory: () -> T? = { null }) =
        this.stateIn(
            viewModelScope,
            SharingStarted.Lazily, factory() ?: T::class.java.newInstance()
        )
}
