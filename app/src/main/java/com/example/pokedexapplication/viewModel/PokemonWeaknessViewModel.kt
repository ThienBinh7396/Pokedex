package com.example.pokedexapplication.viewModel

import android.util.Log
import androidx.databinding.BaseObservable
import com.example.pokedexapplication.Model.WeaknessesResponse
import com.example.pokedexapplication.common.NumberUtils

class PokemonWeaknessViewModel(pokemonWeakness: WeaknessesResponse?) : BaseObservable() {
  private var pokemonWeakness: WeaknessesResponse? = null

  init {
    this.pokemonWeakness = pokemonWeakness
  }

  val pokemonType
    get() = this.pokemonWeakness?.pokemonType

  val effect
    get() = this.pokemonWeakness?.effect.toString()

  val effectText
    get() = "${NumberUtils.formatFloatNumberToFractionType(this.pokemonWeakness?.effect)}x"

  fun setPokemonWeakness(pokemonWeakness: WeaknessesResponse) {
    this.pokemonWeakness = pokemonWeakness
    notifyChange()
  }
}