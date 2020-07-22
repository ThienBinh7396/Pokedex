package com.example.pokedexapplication

import android.app.Application
import com.example.pokedexapplication.Store.Middleware.itemMiddleware
import com.example.pokedexapplication.Store.Middleware.moveMiddleware
import com.example.pokedexapplication.Store.Middleware.pokemonMiddleware
import com.example.pokedexapplication.Store.Reducer.rootReducer
import org.rekotlin.Store

val store = Store(
  reducer = ::rootReducer,
  state = null,
  middleware = listOf(itemMiddleware, moveMiddleware, pokemonMiddleware)
)

class MainApplication: Application()