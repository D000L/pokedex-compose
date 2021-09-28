package com.doool.pokedex.data.mapper

import com.doool.pokedex.data.entity.EvolutionDetail
import com.doool.pokedex.data.entity.EvolvesTo
import com.doool.pokedex.data.entity.InfoEntity
import com.doool.pokedex.data.entity.PokemonEvolutionChainResponse
import com.doool.pokedex.domain.model.Condition
import com.doool.pokedex.domain.model.PokemonEvolutionChain

fun PokemonEvolutionChainResponse.toModel(): List<PokemonEvolutionChain> = with(this) {
  return parseChain(this.chain.species, this.chain.evolvesTo)
}

private fun parseChain(from: InfoEntity, evolvesTo: List<EvolvesTo>): List<PokemonEvolutionChain> {
  val list = mutableListOf<PokemonEvolutionChain>()
  val from = from.toModel()

  evolvesTo.forEach {
    list.add(
      PokemonEvolutionChain(
        from,
        it.species.toModel(),
        it.evolutionDetails.first().toModel()
      )
    )
    if (it.evolvesTo.isNotEmpty()) list.addAll(parseChain(it.species, it.evolvesTo))
  }

  return list
}

fun EvolutionDetail.toModel(): Condition = with(this) {
  Condition(
    item = this.item?.toModel(),
    minLevel = this.minLevel,
    timeOfDay = this.timeOfDay,
    trigger = this.trigger.toModel()
  )
}