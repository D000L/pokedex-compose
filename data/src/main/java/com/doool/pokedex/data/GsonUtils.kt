package com.doool.pokedex.data

import com.google.gson.Gson

inline fun <reified T> T.toJson(): String {
  return Gson().toJson(this, T::class.java)
}

inline fun <reified T> String.toModel(): T {
  return Gson().fromJson(this, T::class.java)
}