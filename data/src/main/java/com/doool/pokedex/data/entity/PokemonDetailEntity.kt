package com.doool.pokedex.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.doool.pokedex.data.dao.PokemonDetailConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "pokemon_detail")
@TypeConverters(PokemonDetailConverter::class)
data class PokemonDetailEntity(
  @PrimaryKey val name: String,
  val height: Int,
  val weight: Int,
  val sprites: Sprites,
  val stats: List<StatEntity>,
  val types: List<TypeEntity>,
  val order: Int
)

data class Sprites(
  @SerializedName("front_default") val frontDefault: String,
  @SerializedName("back_default") val backDefault: String
)

data class StatEntity(
  @SerializedName("base_stat") val amount: Int,
  val effort: Int,
  @SerializedName("stat") val info: StatInfo
)

data class StatInfo(
  val name: String,
  @SerializedName("url") val statInfoUrl: String,
)

data class TypeEntity(
  val slot : Int,
  val type : Type
)

data class Type(
 val name : String,
 val url : String
)