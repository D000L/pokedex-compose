package com.doool.pokedex.data.mapper

import com.doool.pokedex.data.response.PokemonMoveResponse
import com.doool.pokedex.data.response.common.EffectResponse
import com.doool.pokedex.domain.model.Effect
import com.doool.pokedex.domain.model.Info
import com.doool.pokedex.domain.model.PokemonMove

fun PokemonMoveResponse.toModel(): PokemonMove = with(this) {
  PokemonMove(
    id = id,
    name = name,
    accuracy = accuracy,
    effectEntries = effectEntries.firstOrNull()?.toModel(effectChance) ?: Effect(),
    damageClass = damageClass.toModel(),
    flavorTextEntries = flavorTextEntries.map {
      it.flavorText
    },
    learnedPokemon = learnedPokemon.map { it.toModel() },
    machines = machines.firstOrNull()?.machine?.toModel() ?: Info(),
    power = power,
    pp = pp,
    type = type.toModel()
  )
}

fun EffectResponse.toModel(effectChance: Int): Effect = with(this) {
  val effect = effect.replace("\$effect_chance", "$effectChance")
  val shortEffect = shortEffect.replace("\$effect_chance", "$effectChance")
  Effect(effect = effect, effectChance = effectChance, shortEffect = shortEffect)
}