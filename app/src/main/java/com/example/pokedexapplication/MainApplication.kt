package com.example.pokedexapplication

import android.app.Application
import com.example.pokedexapplication.Store.Reducer.rootReducer
import org.rekotlin.Store

val store = Store(
  reducer = ::rootReducer,
  state = null
)

class MainApplication: Application()