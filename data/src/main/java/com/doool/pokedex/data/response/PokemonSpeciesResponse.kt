package com.doool.pokedex.data.response

import com.doool.pokedex.data.response.common.FlavorText
import com.doool.pokedex.data.response.common.InfoResponse
import com.doool.pokedex.data.response.common.Names
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonSpeciesResponse(
    val id: Int = 0,
    val color: InfoResponse = InfoResponse(),
    @SerialName("egg_groups") val eggGroups: List<InfoResponse> = emptyList(),
    @SerialName("evolution_chain") val evolutionChain: EvolutionChain? = null,
    @SerialName("evolves_from_species") val evolvesFromSpecies: InfoResponse? = null,
    @SerialName("flavor_text_entries") val flavorTextEntries: List<FlavorText> = emptyList(),
    @SerialName("form_descriptions") val formDescriptions: List<FormDescription> = emptyList(),
    @SerialName("forms_switchable") val formsSwitchable: Boolean = false,
    @SerialName("gender_rate") val genderRate: Int = 0,
    val genera: List<Genera> = emptyList(),
    val generation: InfoResponse,
    @SerialName("has_gender_differences") val hasGenderDifferences: Boolean = false,
    @SerialName("hatch_counter") val hatchCounter: Int = 0,
    @SerialName("is_baby") val isBaby: Boolean = false,
    @SerialName("is_legendary") val isLegendary: Boolean = false,
    @SerialName("is_mythical") val isMythical: Boolean = false,
    val name: String = "",
    val names: List<Names> = emptyList(),
    val order: Int = 0,
)

@Serializable
data class EvolutionChain(
    val url: String = ""
)

@Serializable
data class Genera(
    val genus: String = "",
    val language: InfoResponse = InfoResponse()
)

@Serializable
data class FormDescription(
    val description: String = "",
    val language: InfoResponse = InfoResponse()
)
