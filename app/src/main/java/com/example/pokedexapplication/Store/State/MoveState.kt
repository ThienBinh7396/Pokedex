package com.example.pokedexapplication.Store.State

import com.example.pokedexapplication.model.Move
import org.rekotlin.StateType

data class MoveState(
  var total: Int = 0,
  var moves: MutableList<Move> = mutableListOf(),
  var searchTotal: Int = 0,
  var searchMoves: MutableList<Move> = mutableListOf(),
  var isLoadingMoves: Boolean = false,
  var isLoadingMoveDetail: Boolean = false,
  var moveDetail: Move? = null
): StateType