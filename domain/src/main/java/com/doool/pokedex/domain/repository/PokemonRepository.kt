package com.doool.pokedex.domain.repository

import com.doool.pokedex.domain.model.Ability
import com.doool.pokedex.domain.model.Form
import com.doool.pokedex.domain.model.Item
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.PokemonEvolutionChain
import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.domain.model.PokemonSpecies
import com.doool.pokedex.domain.model.PokemonTypeResistance

interface PokemonRepository {

    suspend fun getPokemon(id: Int): PokemonDetail
    suspend fun getPokemon(name: String): PokemonDetail
    suspend fun getPokemonSpecies(name: Int): PokemonSpecies
    suspend fun getPokemonEvolutionChain(url: String): List<PokemonEvolutionChain>
    suspend fun getPokemonTypeResistance(name: String): PokemonTypeResistance
    suspend fun getPokemonMove(name: String): PokemonMove
    suspend fun getAbility(name: String): Ability
    suspend fun getItem(name: String): Item
    suspend fun getForm(name: String): Form
}