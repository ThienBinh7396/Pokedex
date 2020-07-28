package com.example.pokedexapplication.Store.Reducer

import com.example.pokedexapplication.Store.Action.ItemAction
import com.example.pokedexapplication.Store.State.ItemState
import org.rekotlin.Action

fun itemReducer(action: Action, itemState: ItemState?): ItemState {
  var _itemState = itemState ?: ItemState()

  when (action) {
    is ItemAction.UPDATE_TOTAL -> {
      _itemState = _itemState.copy(
        total = action.total
      )
    }

    is ItemAction.UPDATE_ITEM_LOADING_STATE -> {
      _itemState = _itemState.copy(
        isLoadingItems = action.isLoadingItems
      )
    }

    is ItemAction.UPDATE_LIST_ITEM -> {
      _itemState = _itemState.copy(
        items = action.items
      )
    }

    is ItemAction.UPDATE_STATE -> {
      if (action.itemState != null) {
        _itemState = _itemState.copy(
          items = action.itemState!!.items,
          total = action.itemState!!.total
        )
      }
    }

    is ItemAction.UPDATE_SEARCH_ITEM_STATE -> {
      if (action.itemState != null) {
        _itemState = _itemState.copy(
          searchItems = action.itemState!!.items.distinctBy { it.name }.toMutableList(),
          searchTotal = action.itemState!!.total
        )
      }
    }
  }
  return _itemState
}
