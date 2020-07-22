package com.example.pokedexapplication.Store.Reducer

import com.example.pokedexapplication.Store.State.RootState
import org.rekotlin.Action

fun rootReducer(action: Action, rootState: RootState?) =
  RootState(
    itemState = itemReducer(action, rootState?.itemState),
    moveState = moveReducer(action, rootState?.moveState),
    appState = appReducer(action, rootState?.appState),
    pokemonState = pokemonReducer(action, rootState?.pokemonState)
  )