package com.doool.pokedex.data.mapper

import com.doool.pokedex.data.response.PokemonMoveResponse
import com.doool.pokedex.data.response.common.EffectResponse
import com.doool.pokedex.domain.Urls
import com.doool.pokedex.domain.model.Effect
import com.doool.pokedex.domain.model.Info
import com.doool.pokedex.domain.model.LocalizedString
import com.doool.pokedex.domain.model.Pokemon
import com.doool.pokedex.domain.model.PokemonMove

fun PokemonMoveResponse.toModel(): PokemonMove = with(this) {
    PokemonMove(
        id = id,
        name = name,
        names = names.map { LocalizedString(it.name, it.language.name) },
        accuracy = accuracy,
        effectEntries = effectEntries.firstOrNull()?.toModel(effectChance) ?: Effect(),
        damageClass = damageClass?.toModel() ?: Info(),
        flavorTextEntries = flavorTextEntries.map {
            LocalizedString(
                it.flavorText.replace("\n", " "),
                it.language.name
            )
        },
        learnedPokemon = learnedPokemon.map {
            val info = it.toModel()
            Pokemon(info.id, info.name, Urls.getImageUrl(info.id))
        },
        machines = machines.firstOrNull()?.machine?.toModel() ?: Info(),
        power = power,
        pp = pp,
        type = type.toModel()
    )
}

fun EffectResponse.toModel(effectChance: Int = -1): Effect = with(this) {
    val effect = effect.replace("\$effect_chance", "$effectChance")
    val shortEffect = shortEffect.replace("\$effect_chance", "$effectChance")
    Effect(effect = effect, effectChance = effectChance, shortEffect = shortEffect)
}