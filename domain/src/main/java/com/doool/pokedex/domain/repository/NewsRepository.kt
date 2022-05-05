package com.doool.pokedex.domain.repository

import com.doool.pokedex.domain.model.PokemonNews

interface NewsRepository {

    suspend fun getNewsList(index: Int, count: Int): List<PokemonNews>
}
