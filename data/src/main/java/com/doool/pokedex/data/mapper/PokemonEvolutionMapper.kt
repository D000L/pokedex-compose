package com.doool.pokedex.data.mapper

import com.doool.pokedex.data.response.EvolutionDetail
import com.doool.pokedex.data.response.EvolvesTo
import com.doool.pokedex.data.response.PokemonEvolutionChainResponse
import com.doool.pokedex.data.response.common.InfoResponse
import com.doool.pokedex.domain.model.Condition
import com.doool.pokedex.domain.model.Info
import com.doool.pokedex.domain.model.LocalizedInfo
import com.doool.pokedex.domain.model.PokemonEvolutionChain

fun PokemonEvolutionChainResponse.toModel(): List<PokemonEvolutionChain> = with(this) {
  return parseChain(this.chain.species, this.chain.evolvesTo)
}

private fun parseChain(
  from: InfoResponse,
  evolvesTo: List<EvolvesTo>
): List<PokemonEvolutionChain> {
  val list = mutableListOf<PokemonEvolutionChain>()
  val from = LocalizedInfo(from.name, url = from.url)

  evolvesTo.forEach {
    list.add(
      PokemonEvolutionChain(
        from,
        LocalizedInfo(it.species.name, url = it.species.url),
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