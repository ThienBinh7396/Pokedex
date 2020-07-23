package com.example.pokedexapplication.Store.Reducer

import android.util.Log
import com.example.pokedexapplication.Store.Action.PokemonAction
import com.example.pokedexapplication.Store.State.PokemonState
import org.rekotlin.Action

fun pokemonReducer(action: Action, pokemonState: PokemonState?): PokemonState {
  var _pokemonState = pokemonState ?: PokemonState()

  when (action) {
    is PokemonAction.UPDATE_STATE ->
      _pokemonState = _pokemonState.copy(
        total = action.state.total,
        pokemons = action.state.pokemons
      )

    is PokemonAction.UPDATE_SEARCH_POKEMONS_STATE -> {
      _pokemonState = _pokemonState.copy(
        searchTotal = action.state.total,
        searchPokemons = action.state.pokemons.distinctBy { it.id }.toMutableList()
      )
    }

    is PokemonAction.UPDATE_POKEMONS_LOADING_STATE ->
      _pokemonState = _pokemonState.copy(
        isLoadingPokemons = action.isLoadingPokemons
      )

    is PokemonAction.UPDATE_DETAIL_LOADING_STATE ->
      _pokemonState = _pokemonState.copy(
        isLoadingPokemonDetail = action.isLoadingPokemonDetail
      )

    is PokemonAction.UPDATE_POKEMON_DETAIL ->
      _pokemonState = _pokemonState.copy(
        pokemonDetail = action.pokemonDetail
      )
  }

  return _pokemonState
}