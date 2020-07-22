package com.example.pokedexapplication.Store.Reducer

import android.util.Log
import com.example.pokedexapplication.Model.API.APIUtils
import com.example.pokedexapplication.Model.ListItemResponse
import com.example.pokedexapplication.Store.Action.ItemAction
import com.example.pokedexapplication.Store.State.ItemState
import org.rekotlin.Action
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun itemReducer(action: Action, itemState: ItemState?): ItemState {
  var _itemState = itemState ?: ItemState()

  when (action) {
    is ItemAction.UPDATE_TOTAL -> {
      _itemState = _itemState.copy(
        total = action.total
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
  }
  return _itemState
}
