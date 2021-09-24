package com.doool.pokedex.presentation.ui.pokemon

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.doool.pokedex.R

enum class PokemonType(@DrawableRes val resId: Int, @ColorRes val colorResId: Int){
  Bug(R.drawable.bug, R.color.bug),
  Dark(R.drawable.dark, R.color.dark),
  Dragon(R.drawable.dragon, R.color.dragon),
  Electric(R.drawable.electric, R.color.electric),
  Fairy(R.drawable.fairy, R.color.fairy),
  Fighting(R.drawable.fighting, R.color.fighting),
  Fire(R.drawable.fire, R.color.fire),
  Flying(R.drawable.flying, R.color.flying),
  Ghost(R.drawable.ghost, R.color.ghost),
  Grass(R.drawable.grass, R.color.grass),
  Ground(R.drawable.ground, R.color.ground),
  Ice(R.drawable.ice, R.color.ice),
  Normal(R.drawable.normal, R.color.normal),
  Poison(R.drawable.poison, R.color.poison),
  Psychic(R.drawable.psychic, R.color.psychic),
  Rock(R.drawable.rock, R.color.rock),
  Steel(R.drawable.steel, R.color.steel),
  Water(R.drawable.water, R.color.water)
}