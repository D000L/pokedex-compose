package com.doool.pokedex.data.mapper

import com.doool.pokedex.data.entity.PokemonMoveEntity
import com.doool.pokedex.data.response.PokemonMoveResponse
import com.doool.pokedex.data.response.common.EffectResponse
import com.doool.pokedex.data.toResponse
import com.doool.pokedex.domain.model.Effect
import com.doool.pokedex.domain.model.PokemonMove

fun PokemonMoveEntity.toModel(): PokemonMove = with(this) {
  json?.toResponse<PokemonMoveResponse>()?.toModel() ?: PokemonMove(name = name).apply {
    isPlaceholder = true
  }
}

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