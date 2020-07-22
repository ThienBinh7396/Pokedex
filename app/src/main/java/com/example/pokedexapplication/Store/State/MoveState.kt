package com.example.pokedexapplication.Store.State

import com.example.pokedexapplication.Model.Move
import org.rekotlin.StateType

data class MoveState(
  var total: Int = 0,
  var moves: MutableList<Move> = mutableListOf()
): StateType