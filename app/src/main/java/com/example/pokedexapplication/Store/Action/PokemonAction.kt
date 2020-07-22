package com.example.pokedexapplication.Store.Action

import com.example.pokedexapplication.Model.ListPokemonResponse
import org.rekotlin.Action

sealed class PokemonAction : Action {
  class UPDATE_STATE(var state: ListPokemonResponse) : Action
  class FETCH_DATA(
    var name: String = "",
    var page: Int? = null,
    var isSearching: Boolean = false,
    var isFirstFetch: Boolean = false
  ): Action
}