package com.doool.pokedex.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class ItemEntity(
  @PrimaryKey val name: String,
  val index: Int,
  val id: Int = -1,
  val json: String? = null
)