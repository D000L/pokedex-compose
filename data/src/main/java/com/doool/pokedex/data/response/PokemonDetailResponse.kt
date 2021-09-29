package com.doool.pokedex.data.response


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PokemonDetailResponse(
  val abilities: List<Ability> = listOf(),
  @SerializedName("base_experience") val baseExperience: Int = 0,
  val forms: List<InfoResponse> = listOf(),
  @SerializedName("game_indices") val gameIndices: List<GameIndice> = listOf(),
  val height: Int = 0,
  @SerializedName("held_items") val heldItems: List<Any> = listOf(),
  val id: Int = 0,
  @SerializedName("is_default") val isDefault: Boolean = false,
  @SerializedName("location_area_encounters") val locationAreaEncounters: String = "",
  val moves: List<MoveResponse> = listOf(),
  val name: String = "",
  val order: Int = 0,
  @SerializedName("past_types") val pastTypes: List<Any> = listOf(),
  val species: InfoResponse = InfoResponse(),
  val sprites: Sprites = Sprites(),
  val stats: List<StatResponse> = listOf(),
  val types: List<TypeResponse> = listOf(),
  val weight: Int = 0,
  val color: InfoResponse = InfoResponse()
)

@Keep
data class Ability(
  val ability: InfoResponse = InfoResponse(),
  @SerializedName("is_hidden") val isHidden: Boolean = false,
  val slot: Int = 0
)

@Keep
data class GameIndice(
  @SerializedName("game_index") val gameIndex: Int = 0,
  val version: InfoResponse = InfoResponse()
)

@Keep
data class MoveResponse(
  val move: InfoResponse = InfoResponse(),
  @SerializedName("version_group_details") val versionGroupDetails: List<VersionGroupDetailResponse> = listOf()
)

@Keep
data class VersionGroupDetailResponse(
  @SerializedName("level_learned_at") val levelLearnedAt: Int = 0,
  @SerializedName("move_learn_method") val moveLearnMethod: InfoResponse = InfoResponse(),
  @SerializedName("version_group") val versionGroup: InfoResponse = InfoResponse()
)

@Keep
data class Sprites(
  @SerializedName("front_default") val frontDefault: String = "",
  @SerializedName("back_default") val backDefault: String = "",
  val other: SpritesOthers = SpritesOthers()
)

@Keep
data class SpritesOthers(
  @SerializedName("official-artwork") val artwork: Artwork = Artwork(),
){
  @Keep
  data class Artwork(
    @SerializedName("front_default") val frontDefault: String = "",
  )
}

@Keep
data class StatResponse(
  @SerializedName("base_stat") val baseStat: Int = 0,
  val effort: Int = 0,
  val stat: InfoResponse = InfoResponse()
)

@Keep
data class TypeResponse(
  val slot: Int = 0,
  val type: InfoResponse = InfoResponse()
)