package com.doool.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.doool.pokedex.domain.usecase.GetPokemonNews
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val getPokemonNews: GetPokemonNews) : ViewModel() {

    val news = Pager(
        PagingConfig(
            6,
            prefetchDistance = 2,
            initialLoadSize = 6,
            enablePlaceholders = false
        )
    ) {
        NewsPagingSource(getPokemonNews)
    }.flow.cachedIn(viewModelScope)
}

