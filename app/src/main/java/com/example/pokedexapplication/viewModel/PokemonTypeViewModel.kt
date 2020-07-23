package com.example.pokedexapplication.viewModel

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableArrayList
import com.example.pokedexapplication.Model.Pokemon
import java.util.*

class PokemonTypeViewModel(): BaseObservable() {
  private var mPokemonType = ObservableArrayList<MutableList<String>>()
  init {
    this.mPokemonType.addAll(0, listOf())
  }

  fun setPokemonType(mPokemonType: MutableList<String>) {
    this.mPokemonType.addAll(0, listOf(mPokemonType))
    notifyChange()
  }

  fun getPokemonType(): ObservableArrayList<MutableList<String>>? {
    return this.mPokemonType
  }

}