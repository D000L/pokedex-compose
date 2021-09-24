package com.doool.pokedex.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> T.toJson(): String {
  return Gson().toJson(this, object : TypeToken<T>() {}.type)
}

inline fun <reified T> String.toModel(): T {
  return Gson().fromJson(this, object : TypeToken<T>() {}.type)
}