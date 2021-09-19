package com.doool.pokedex.presentation.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.doool.pokedex.domain.model.Pokemon
import com.doool.pokedex.domain.usecase.GetPokemonList

class PokemonPagingSource(
  private val getPokemonList: GetPokemonList
) : PagingSource<Int, Pokemon>() {

  override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int = 0

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
    val key = params.key ?: 0
    getPokemonList(key).fold({
      val nextKey = if (it.count() == 0) null else key + it.count()
      Log.d("asfdasdfasf","$nextKey")
      return LoadResult.Page(it, null, nextKey)
    }, {
      return LoadResult.Error(it)
    })
  }
}