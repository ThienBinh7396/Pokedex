package com.example.pokedexapplication.Store.State

import org.rekotlin.StateType

data class RootState (
  var itemState: ItemState,
  var moveState: MoveState,
  var appState: AppState,
  var pokemonState: PokemonState
): StateType