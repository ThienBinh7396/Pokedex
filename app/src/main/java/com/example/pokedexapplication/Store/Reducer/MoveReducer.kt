package com.example.pokedexapplication.Store.Reducer

import com.example.pokedexapplication.Store.Action.MoveAction
import com.example.pokedexapplication.Store.State.MoveState
import org.rekotlin.Action

fun moveReducer(action: Action, moveState: MoveState?): MoveState {
  var _moveState = moveState ?: MoveState()

  when (action) {
    is MoveAction.UPDATE_TOTAL ->
      _moveState.total = action.total

    is MoveAction.UPDATE_LIST_MOVES ->
      _moveState.moves = action.moves
  }

  return _moveState
}