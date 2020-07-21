package com.example.pokedexapplication.Store.Action

import com.example.pokedexapplication.Model.Move
import org.rekotlin.Action

sealed class MoveAction : Action {
  class UPDATE_TOTAL(var total: Int) : Action
  class UPDATE_LIST_MOVES(var moves: MutableList<Move>) : Action
}