package com.doool.pokedex.data.mapper

import com.doool.pokedex.data.parseId
import com.doool.pokedex.data.response.AbilityInfoResponse
import com.doool.pokedex.data.response.MoveResponse
import com.doool.pokedex.data.response.PokemonDetailResponse
import com.doool.pokedex.data.response.StatResponse
import com.doool.pokedex.data.response.TypeResponse
import com.doool.pokedex.data.response.VersionGroupDetailResponse
import com.doool.pokedex.domain.model.AbilityInfo
import com.doool.pokedex.domain.model.Info
import com.doool.pokedex.domain.model.Move
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.Stat
import com.doool.pokedex.domain.model.VersionGroupDetail

fun PokemonDetailResponse.toModel(): PokemonDetail = with(this) {
    PokemonDetail(
        id = id,
        name = name,
        height = height,
        weight = weight,
        image = sprites.other.artwork.frontDefault ?: sprites.frontDefault,
        species = species.toModel(),
        stats = stats.map {
            it.toModel()
        },
        types = types.map {
            it.toModel()
        },
        moves = moves.map {
            it.toModel()
        },
        abilities = abilities.map {
            it.toModel()
        }
    )
}

fun StatResponse.toModel(): Stat = with(this) {
    Stat(amount = baseStat, name = stat.name, infoUrl = stat.url)
}

fun TypeResponse.toModel(): Info = with(this) {
    Info(name = type.name, id = type.url.parseId())
}

fun MoveResponse.toModel(): Move = with(this) {
    Move(name = move.name, url = move.url, details = versionGroupDetails.map {
        it.toModel()
    })
}

fun VersionGroupDetailResponse.toModel(): VersionGroupDetail = with(this) {
    VersionGroupDetail(
        learnLevel = levelLearnedAt,
        learnMethod = moveLearnMethod.toModel(),
        version = versionGroup.toModel()
    )
}

fun AbilityInfoResponse.toModel(): AbilityInfo = with(this) {
    AbilityInfo(
        ability = ability.toModel(),
        isHidden = isHidden,
        slot = slot
    )
}
