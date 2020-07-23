package com.example.pokedexapplication.Store.Action

import com.example.pokedexapplication.Model.ListPokemonResponse
import com.example.pokedexapplication.Model.PokemonDetail
import org.rekotlin.Action

sealed class PokemonAction : Action {
  class UPDATE_STATE(var state: ListPokemonResponse) : Action
  class UPDATE_SEARCH_POKEMONS_STATE(var state: ListPokemonResponse) : Action
  class FETCH_DATA(
    var name: String = "",
    var page: Int? = null,
    var isSearching: Boolean = false,
    var isFirstFetch: Boolean = false
  ): Action
  class UPDATE_POKEMONS_LOADING_STATE(var isLoadingPokemons: Boolean): Action
  class UPDATE_DETAIL_LOADING_STATE(var isLoadingPokemonDetail: Boolean): Action
  class UPDATE_POKEMON_DETAIL(var pokemonDetail: PokemonDetail?): Action
  class FETCH_POKEMON_DETAIL(var id: String): Action
}