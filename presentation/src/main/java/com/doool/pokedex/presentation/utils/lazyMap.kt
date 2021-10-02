package com.doool.pokedex.presentation.utils

fun <K, V> lazyMap(initializer: (K) -> V): Map<K, V> {
  val map = mutableMapOf<K, V>()
  return map.withDefault { key ->
    return@withDefault map.getOrPut(key) { initializer(key) }
  }
}