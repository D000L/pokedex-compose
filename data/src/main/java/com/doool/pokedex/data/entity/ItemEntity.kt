package com.doool.pokedex.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class ItemEntity(
  @PrimaryKey val name: String,
  val id: Int = 0,
  val json: String? = null
)