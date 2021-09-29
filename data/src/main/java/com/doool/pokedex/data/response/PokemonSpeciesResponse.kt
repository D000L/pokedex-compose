package com.doool.pokedex.data.response

import androidx.annotation.Keep
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Keep
data class PokemonSpeciesResponse(
  @PrimaryKey val id: Int = 0,
  val color: InfoEntity = InfoEntity(),
  @SerializedName("egg_groups") val eggGroups: List<InfoEntity> = emptyList(),
  @SerializedName("evolution_chain") val evolutionChain: EvolutionChain? = null,
  @SerializedName("evolves_from_species") val evolvesFromSpecies: InfoEntity? = null,
  @SerializedName("flavor_text_entries") val flavorTextEntries: List<FlavorTextEntries> = emptyList(),
  @SerializedName("form_descriptions") val formDescriptions: List<String> = emptyList(),
  @SerializedName("forms_switchable") val formsSwitchable: Boolean = false,
  @SerializedName("gender_rate") val genderRate: Int = 0,
  val genera: List<Genera> = emptyList(),
  val generation: InfoEntity,
  @SerializedName("has_gender_differences") val hasGenderDifferences: Boolean = false,
  @SerializedName("hatch_counter") val hatchCounter: Int = 0,
  @SerializedName("is_baby") val isBaby: Boolean = false,
  @SerializedName("is_legendary") val isLegendary: Boolean = false,
  @SerializedName("is_mythical") val isMythical: Boolean = false,
  val name: String = "",
  val names: List<Names> = emptyList(),
  val order: Int = 0,
)

@Keep
data class EvolutionChain(
  val url: String = ""
)

@Keep
data class Genera(
  val genus: String = "",
  val language: InfoEntity = InfoEntity()
)

@Keep
data class FlavorTextEntries(
  @SerializedName("flavor_text") val flavorText: String = "",
  val language: InfoEntity = InfoEntity(),
  val version: InfoEntity = InfoEntity()
)

@Keep
data class Names(
  val language: InfoEntity = InfoEntity(),
  val name: String = ""
)