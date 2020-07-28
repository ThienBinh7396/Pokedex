package com.example.pokedexapplication.Store.Action

import com.example.pokedexapplication.model.Item
import com.example.pokedexapplication.model.ListItemResponse
import org.rekotlin.Action

sealed class ItemAction : Action {
  class UPDATE_TOTAL(var total: Int) : Action
  class UPDATE_LIST_ITEM(var items: MutableList<Item>) : Action
  class UPDATE_STATE(var itemState: ListItemResponse?) : Action
  class UPDATE_SEARCH_ITEM_STATE(var itemState: ListItemResponse?) : Action
  class FETCH_DATA(
    var page: Int? = null,
    var name: String = "",
    var isSearching: Boolean = false,
    var isFirstFetch: Boolean = false
  ) : Action

  class UPDATE_ITEM_LOADING_STATE(var isLoadingItems: Boolean): Action
}