package com.doool.pokedex.data.response


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PokemonDetailResponse(
  val abilities: List<Ability> = listOf(),
  @SerializedName("base_experience") val baseExperience: Int = 0,
  val forms: List<InfoEntity> = listOf(),
  @SerializedName("game_indices") val gameIndices: List<GameIndice> = listOf(),
  val height: Int = 0,
  @SerializedName("held_items") val heldItems: List<Any> = listOf(),
  val id: Int = 0,
  @SerializedName("is_default") val isDefault: Boolean = false,
  @SerializedName("location_area_encounters") val locationAreaEncounters: String = "",
  val moves: List<Move> = listOf(),
  val name: String = "",
  val order: Int = 0,
  @SerializedName("past_types") val pastTypes: List<Any> = listOf(),
  val species: InfoEntity = InfoEntity(),
  val sprites: Sprites = Sprites(),
  val stats: List<StatEntity> = listOf(),
  val types: List<TypeEntity> = listOf(),
  val weight: Int = 0
)

@Keep
data class Ability(
  val ability: InfoEntity = InfoEntity(),
  @SerializedName("is_hidden") val isHidden: Boolean = false,
  val slot: Int = 0
)

@Keep
data class GameIndice(
  @SerializedName("game_index") val gameIndex: Int = 0,
  val version: InfoEntity = InfoEntity()
)

@Keep
data class Move(
  val move: InfoEntity = InfoEntity(),
  @SerializedName("version_group_details") val versionGroupDetails: List<VersionGroupDetail> = listOf()
)

@Keep
data class VersionGroupDetail(
  @SerializedName("level_learned_at") val levelLearnedAt: Int = 0,
  @SerializedName("move_learn_method") val moveLearnMethod: InfoEntity = InfoEntity(),
  @SerializedName("version_group") val versionGroup: InfoEntity = InfoEntity()
)

data class Sprites(
  @SerializedName("front_default") val frontDefault: String = "",
  @SerializedName("back_default") val backDefault: String = ""
)

@Keep
data class StatEntity(
  @SerializedName("base_stat") val baseStat: Int = 0,
  val effort: Int = 0,
  val stat: InfoEntity = InfoEntity()
)

@Keep
data class TypeEntity(
  val slot: Int = 0,
  val type: InfoEntity = InfoEntity()
)