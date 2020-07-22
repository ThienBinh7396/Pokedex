package com.example.pokedexapplication.Store.Action

import com.example.pokedexapplication.Model.ListMoveResponse
import com.example.pokedexapplication.Model.Move
import org.rekotlin.Action

sealed class MoveAction : Action {
  class UPDATE_TOTAL(var total: Int) : Action
  class UPDATE_LIST_MOVES(var moves: MutableList<Move>) : Action
  class UPDATE_STATE(var state: ListMoveResponse): Action
  class FETCH_DATA(
    var page: Int? = null,
    var name: String = "",
    var isSearching: Boolean = false,
    var isFirstFetch: Boolean = false
  ) : Action
}