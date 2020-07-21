package com.example.pokedexapplication.Store.Reducer

import com.example.pokedexapplication.Store.Action.ItemAction
import com.example.pokedexapplication.Store.State.ItemState
import org.rekotlin.Action

fun itemReducer(action: Action, itemState: ItemState?): ItemState {
  var _itemState = itemState ?: ItemState()

  when(action){
    is ItemAction.UPDATE_TOTAL -> {
      _itemState.total = action.total
    }

    is ItemAction.UPDATE_LIST_ITEM -> {
      _itemState.items = action.items
    }
  }

  return _itemState
}
