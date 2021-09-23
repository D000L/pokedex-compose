package com.doool.pokedex.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "pokemon_species")
data class PokemonSpeciesEntity(
  @PrimaryKey val id: Int,
  val name: String,
  val json: String,
)

data class PokemonSpeciesResponse(
  @PrimaryKey val id: Int,
  val color: InfoEntity,
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

data class EvolutionChain(
  val url: String
)

data class Genera(
  val genus: String,
  val language: InfoEntity
)

data class FlavorTextEntries(
  @SerializedName("flavor_text") val flavorText: String,
  val language: InfoEntity,
  val version: InfoEntity
)

data class Names(
  val language: InfoEntity,
  val name: String
)

data class InfoEntity(
  val name: String,
  val url: String
)