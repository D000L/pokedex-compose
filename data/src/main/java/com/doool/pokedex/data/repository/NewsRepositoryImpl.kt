package com.doool.pokedex.data.repository

import com.doool.pokedex.data.mapper.toModel
import com.doool.pokedex.data.service.NewsService
import com.doool.pokedex.domain.model.PokemonNews
import com.doool.pokedex.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsService: NewsService
) : NewsRepository {

    override suspend fun getNewsList(index: Int, count: Int): List<PokemonNews> {
        return newsService.getNewsList(index, count).map { it.toModel() }
    }
}
