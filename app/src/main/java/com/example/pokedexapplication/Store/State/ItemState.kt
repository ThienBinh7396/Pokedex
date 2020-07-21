package com.example.pokedexapplication.Store.State

import com.example.pokedexapplication.Model.Item
import org.rekotlin.StateType

class ItemState(
  var total: Int = 0,
  var items: MutableList<Item> = mutableListOf()
): StateType