package com.example.pokedexapplication.Store.State

import org.rekotlin.StateType

data class FirstFetchData(
  var isItemFirstFetch: Boolean = false,
  var isMoveFirstFetch: Boolean = false,
  var isPokemonFirstFetch: Boolean = false
)

data class AppState(
  var isFetching: Boolean = false,
  var isFirstFetchData: FirstFetchData = FirstFetchData()
) : StateType