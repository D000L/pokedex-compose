package com.doool.pokedex.data.repository

import com.doool.pokedex.data.dao.PokemonDao
import com.doool.pokedex.data.dao.PokemonDetailDao
import com.doool.pokedex.data.entity.AbilityEntity
import com.doool.pokedex.data.entity.FormEntity
import com.doool.pokedex.data.entity.ItemEntity
import com.doool.pokedex.data.entity.PokemonDetailEntity
import com.doool.pokedex.data.entity.PokemonEvolutionChainEntity
import com.doool.pokedex.data.entity.PokemonMoveEntity
import com.doool.pokedex.data.entity.PokemonSpeciesEntity
import com.doool.pokedex.data.entity.PokemonTypeResistanceEntity
import com.doool.pokedex.data.mapper.toModel
import com.doool.pokedex.data.response.AbilityResponse
import com.doool.pokedex.data.response.FormResponse
import com.doool.pokedex.data.response.ItemResponse
import com.doool.pokedex.data.response.PokemonDetailResponse
import com.doool.pokedex.data.response.PokemonEvolutionChainResponse
import com.doool.pokedex.data.response.PokemonMoveResponse
import com.doool.pokedex.data.response.PokemonSpeciesResponse
import com.doool.pokedex.data.response.PokemonTypeResistanceResponse
import com.doool.pokedex.data.service.PokeApiService
import com.doool.pokedex.data.toJson
import com.doool.pokedex.data.toResponse
import com.doool.pokedex.domain.model.Ability
import com.doool.pokedex.domain.model.Form
import com.doool.pokedex.domain.model.Item
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.PokemonEvolutionChain
import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.domain.model.PokemonSpecies
import com.doool.pokedex.domain.model.PokemonTypeResistance
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokeApiService: PokeApiService,
    private val pokemonDetailDao: PokemonDetailDao,
    private val pokemonDao: PokemonDao
) : PokemonRepository {

    override suspend fun getPokemon(id: Int): PokemonDetail {
        return fetchPokemon(id).toModel()
    }

    private suspend fun fetchPokemon(id: Int): PokemonDetailResponse {
        val localResult = pokemonDetailDao.getPokemon(id)

        if (localResult.json == null) {
            val remoteResult = pokeApiService.getPokemon(id)
            pokemonDetailDao.insertPokemonDetail(
                PokemonDetailEntity(
                    remoteResult.name,
                    remoteResult.id,
                    remoteResult.toJson()
                )
            )
            return remoteResult
        }
        return localResult.json.toResponse()
    }

    override suspend fun getPokemon(name: String): PokemonDetail {
        return fetchPokemon(name).toModel()
    }

    private suspend fun fetchPokemon(name: String): PokemonDetailResponse {
        val localResult = pokemonDetailDao.getPokemon(name)

        if (localResult.json == null) {
            val remoteResult = pokeApiService.getPokemon(name)
            pokemonDetailDao.insertPokemonDetail(
                PokemonDetailEntity(
                    remoteResult.name,
                    remoteResult.id,
                    remoteResult.toJson()
                )
            )
            return remoteResult
        }
        return localResult.json.toResponse()
    }

    override suspend fun getPokemonSpecies(id: Int): PokemonSpecies {
        return fetchPokemonSpecies(id).toModel()
    }

    private suspend fun fetchPokemonSpecies(id: Int): PokemonSpeciesResponse {
        val localResult = pokemonDao.getPokemonSpecies(id)

        if (localResult == null) {
            val remoteResult = pokeApiService.getPokemonSpecies(id)
            val model = remoteResult.toJson()
            pokemonDao.insertPokemonSpecies(
                PokemonSpeciesEntity(
                    remoteResult.id,
                    remoteResult.name,
                    model
                )
            )
            return remoteResult
        }
        return localResult.json.toResponse()
    }

    override suspend fun getPokemonEvolutionChain(url: String): List<PokemonEvolutionChain> {
        return fetchPokemonEvolutionChain(parseEvolutionChainId(url)).toModel()
    }

    private fun parseEvolutionChainId(url: String): Int {
        return url.removePrefix("https://pokeapi.co/api/v2/evolution-chain/").removeSuffix("/")
            .toInt()
    }

    private suspend fun fetchPokemonEvolutionChain(id: Int): PokemonEvolutionChainResponse {
        val localResult = pokemonDao.getPokemonEvolutionChain(id)

        if (localResult == null) {
            val remoteResult = pokeApiService.getPokemonEvolutionChain(id)
            val model = remoteResult.toJson()
            pokemonDao.insertPokemonEvolutionChain(
                PokemonEvolutionChainEntity(
                    remoteResult.id,
                    model
                )
            )
            return remoteResult
        }
        return localResult.json.toResponse()
    }

    override suspend fun getPokemonTypeResistance(name: String): PokemonTypeResistance {
        return fetchPokemonTypeResistance(name).toModel()
    }

    private suspend fun fetchPokemonTypeResistance(name: String): PokemonTypeResistanceResponse {
        val localResult = pokemonDao.getPokemonTypeResistanceEntity(name)

        if (localResult == null) {
            val remoteResult = pokeApiService.getPokemonTypeResistance(name)
            val model = remoteResult.toJson()
            pokemonDao.insertPokemonTypeResistanceEntity(
                PokemonTypeResistanceEntity(
                    remoteResult.id,
                    remoteResult.name,
                    model
                )
            )
            return remoteResult
        }
        return localResult.json.toResponse()
    }

    override suspend fun getPokemonMove(name: String): PokemonMove {
        return fetchMove(name).toModel()
    }

    private suspend fun fetchMove(names: String): PokemonMoveResponse {
        val localResult = pokemonDao.getPokemonMoveEntity(names)

        if (localResult?.json == null) {
            val remoteResult = pokeApiService.getPokemonMove(names)
            pokemonDao.insertPokemonMoveEntity(
                PokemonMoveEntity(
                    remoteResult.name,
                    remoteResult.id,
                    remoteResult.toJson()
                )
            )
            return remoteResult
        }
        return localResult.json.toResponse()
    }

    override suspend fun getItem(name: String): Item {
        return fetchItem(name).toModel()
    }

    private suspend fun fetchItem(names: String): ItemResponse {
        val localResult = pokemonDao.getItemEntity(names)

        if (localResult?.json == null) {
            val remoteResult = pokeApiService.getItem(names)
            pokemonDao.insertItemEntity(
                ItemEntity(
                    remoteResult.name,
                    remoteResult.id,
                    remoteResult.toJson()
                )
            )
            return remoteResult
        }
        return localResult.json.toResponse()
    }

    override suspend fun getAbility(name: String): Ability {
        return fetchAbility(name).toModel()
    }

    private suspend fun fetchAbility(names: String): AbilityResponse {
        val localResult = pokemonDao.getAbility(names)

        if (localResult?.json == null) {
            val remoteResult = pokeApiService.getAbility(names)
            pokemonDao.insertAbilityEntity(
                AbilityEntity(
                    remoteResult.name,
                    remoteResult.id,
                    remoteResult.toJson()
                )
            )
            return remoteResult
        }
        return localResult.json.toResponse()
    }

    override suspend fun getForm(name: String): Form {
        return fetchForm(name).toModel()
    }

    private suspend fun fetchForm(name: String): FormResponse {
        val localResult = pokemonDao.getForm(name)

        if (localResult?.json == null) {
            val remoteResult = pokeApiService.getForm(name)
            pokemonDao.insertFormEntity(
                FormEntity(
                    remoteResult.name,
                    remoteResult.id,
                    remoteResult.toJson()
                )
            )
            return remoteResult
        }
        return localResult.json.toResponse()
    }
}