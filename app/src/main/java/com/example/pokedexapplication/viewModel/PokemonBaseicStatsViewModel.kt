package com.example.pokedexapplication.viewModel

import androidx.databinding.BaseObservable
import com.example.pokedexapplication.model.PokemonBasicStat

class PokemonBaseicStatsViewModel(pokemonBasicStat: PokemonBasicStat): BaseObservable() {
  private var mPokemonBasicStat: PokemonBasicStat = pokemonBasicStat

  val maxValue
    get() = this.mPokemonBasicStat.maxValue

  val value
    get() = this.mPokemonBasicStat.value

  val valueText
    get() = "${this.mPokemonBasicStat.value}"

  val name
    get() = this.mPokemonBasicStat.name

}