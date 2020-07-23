package com.example.pokedexapplication.Store.Action

import com.example.pokedexapplication.Store.State.FirstFetchData
import org.rekotlin.Action

sealed class AppAction : Action {
  class UPDATE_IS_FETCHING(var isFetching: Boolean) : Action
  class UPDATE_IS_SEARCHING(var isSearching: Boolean) : Action
  class UPDATE_FIRST_FETCH(var isFirstFetchData: FirstFetchData) : Action
  class UPDATE_TITLE_MAIN_ACTIVITY(var title: String) : Action
  class UPDATE_SEARCH_QUERY(var query: String) : Action
  class UPDATE_LAST_SEARCH_QUERY(var query: String) : Action

}