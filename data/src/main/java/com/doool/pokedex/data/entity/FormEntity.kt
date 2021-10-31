package com.doool.pokedex.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "form")
data class FormEntity(
  @PrimaryKey val name: String,
  val id: Int,
  val json: String,
)