package com.doool.pokedex.data.mapper

import com.doool.pokedex.data.response.PokemonNewsResponse
import com.doool.pokedex.domain.model.PokemonNews

fun PokemonNewsResponse.toModel(): PokemonNews = with(this) {
    PokemonNews(title, shortDescription, url, image, date, tags)
}