package com.doool.pokedex.data.dao

import androidx.room.TypeConverter
import com.doool.pokedex.data.entity.Sprites
import com.doool.pokedex.data.entity.StatEntity
import com.doool.pokedex.data.entity.StatInfo
import com.doool.pokedex.data.entity.TypeEntity
import com.doool.pokedex.domain.model.Stat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PokemonDetailConverter {

  @TypeConverter
  fun fromSprites(value: Sprites): String {
    val gson = Gson()
    val type = object : TypeToken<Sprites>() {}.type
    return gson.toJson(value, type)
  }

  @TypeConverter
  fun toSprites(value: String): Sprites {
    val gson = Gson()
    val type = object : TypeToken<Sprites>() {}.type
    return gson.fromJson(value, type)
  }

  @TypeConverter
  fun fromStatList(value:List<StatEntity>): String {
    val gson = Gson()
    val type = object : TypeToken<List<StatEntity>>() {}.type
    return gson.toJson(value, type)
  }

  @TypeConverter
  fun toStatList(value: String): List<StatEntity> {
    val gson = Gson()
    val type = object : TypeToken<List<StatEntity>>() {}.type
    return gson.fromJson(value, type)
  }

  @TypeConverter
  fun fromTypeList(value:List<TypeEntity>): String {
    val gson = Gson()
    val type = object : TypeToken<List<TypeEntity>>() {}.type
    return gson.toJson(value, type)
  }

  @TypeConverter
  fun toTypeList(value: String): List<TypeEntity> {
    val gson = Gson()
    val type = object : TypeToken<List<TypeEntity>>() {}.type
    return gson.fromJson(value, type)
  }

  @TypeConverter
  fun fromStatInfo(value: StatInfo): String {
    val gson = Gson()
    val type = object : TypeToken<StatInfo>() {}.type
    return gson.toJson(value, type)
  }

  @TypeConverter
  fun toStatInfo(value: String): StatInfo {
    val gson = Gson()
    val type = object : TypeToken<StatInfo>() {}.type
    return gson.fromJson(value, type)
  }
}