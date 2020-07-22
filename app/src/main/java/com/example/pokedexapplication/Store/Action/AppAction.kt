package com.example.pokedexapplication.Store.Action

import com.example.pokedexapplication.Store.State.FirstFetchData
import org.rekotlin.Action

sealed class AppAction : Action {
  class UPDATE_IS_FETCHING(var isFetching: Boolean) : Action
  class UPDATE_FIRST_FETCH(var isFirstFetchData: FirstFetchData): Action
}