package com.example.pokedexapplication.viewModel

import android.util.Log
import androidx.databinding.BaseObservable
import com.example.pokedexapplication.Model.PokemonDetail

class PokemonDetailViewModel : BaseObservable() {
  private var pokemonDetail: PokemonDetail? = null
  private var mIsLoading: Boolean = true

  val stats
    get() = this.pokemonDetail?.stats
  val moves
    get() = this.pokemonDetail?.moves

  val pokemon
    get() = this.pokemonDetail?.pokemon
  val pokemonTypes
    get() = this.pokemonDetail?.pokemon?.pokemonTypes

  val pokemonWeaknesses
    get() = this.pokemonDetail?.stats?.weaknesses

  val pokemonIdText
    get() = "#${this.pokemonDetail?.pokemon?.id}"

  val isLoading
    get() = this.mIsLoading

  fun setIsLoading(isLoading: Boolean) {
    this.mIsLoading = isLoading
    notifyChange()
  }

  fun setPokemonDetail(pokemonDetail: PokemonDetail?) {
    this.pokemonDetail = pokemonDetail
    notifyChange()
  }

}