package com.example.pokedexapplication.Store.Action

import com.example.pokedexapplication.Model.Item
import org.rekotlin.Action

sealed class ItemAction: Action {
  class UPDATE_TOTAL(var total: Int): Action
  class UPDATE_LIST_ITEM(var items: MutableList<Item>): Action
  class FETCH_DATA(): Action
}