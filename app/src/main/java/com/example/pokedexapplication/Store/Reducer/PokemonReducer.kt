package com.example.pokedexapplication.Store.Reducer

import com.example.pokedexapplication.Store.Action.PokemonAction
import com.example.pokedexapplication.Store.State.PokemonState
import org.rekotlin.Action

fun pokemonReducer(action: Action, pokemonState: PokemonState?): PokemonState{
  var _pokemonState = pokemonState ?: PokemonState()

  when(action){
    is PokemonAction.UPDATE_STATE ->
      _pokemonState = _pokemonState.copy(
        total = action.state.total,
        pokemons = action.state.pokemons
      )
  }

  return _pokemonState
}