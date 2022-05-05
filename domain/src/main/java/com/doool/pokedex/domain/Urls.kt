package com.doool.pokedex.domain

object Urls {
    fun getImageUrl(id: Int): String =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
}