package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.model.PokemonNews
import com.doool.pokedex.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPokemonNews @Inject constructor(private val newsRepository: NewsRepository) {

  suspend operator fun invoke(index: Int, count: Int): List<PokemonNews> =
    newsRepository.getNewsList(index, count)
}