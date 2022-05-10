package com.doool.pokedex.presentation.ui.news

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.doool.pokedex.domain.model.PokemonNews
import com.doool.pokedex.domain.usecase.GetPokemonNews

class NewsPagingSource(private val getPokemonNews: GetPokemonNews) :
    PagingSource<Int, PokemonNews>() {

    override fun getRefreshKey(state: PagingState<Int, PokemonNews>): Int = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonNews> {
        val key = params.key ?: 0

        return try {
            val result = getPokemonNews(key, params.loadSize)
            LoadResult.Page(result, null, key + params.loadSize)
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }
}
