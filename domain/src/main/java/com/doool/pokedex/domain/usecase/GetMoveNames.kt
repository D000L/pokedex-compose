package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.model.Info
import com.doool.pokedex.domain.repository.SearchRepository
import javax.inject.Inject

class GetMoveNames @Inject constructor(private val searchRepository: SearchRepository) {

  suspend operator fun invoke(query: String? = null): List<Info> =
    searchRepository.searchMoveNames(query)
}