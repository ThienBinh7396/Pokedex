package com.example.pokedexapplication.Store.Reducer

import com.example.pokedexapplication.Store.Action.MoveAction
import com.example.pokedexapplication.Store.State.MoveState
import org.rekotlin.Action

fun moveReducer(action: Action, moveState: MoveState?): MoveState {
  var _moveState = moveState ?: MoveState()

  when (action) {
    is MoveAction.UPDATE_TOTAL ->
      _moveState = _moveState.copy(
        total = action.total
      )

    is MoveAction.UPDATE_LIST_MOVES ->
      _moveState = _moveState.copy(
        moves = action.moves
      )
    is MoveAction.UPDATE_STATE ->
      _moveState = _moveState.copy(
        total = action.state.total,
        moves = action.state.moves
      )
    is MoveAction.UPDATE_MOVES_LOADING_STATE ->
      _moveState = _moveState.copy(
        isLoadingMoves = action.isLoadingMoves
      )
  }

  return _moveState
}