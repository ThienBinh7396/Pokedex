package com.example.pokedexapplication.viewModel

import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapplication.Model.WeaknessesResponse
import com.example.pokedexapplication.adapter.RecyclerView.PokemonWeaknessAdapter

class PokemonWeaknessesViewModel : BaseObservable() {
  private var pokemonWeaknesses: ObservableArrayList<MutableList<WeaknessesResponse>> =
    ObservableArrayList()

  init {
    pokemonWeaknesses.addAll(0, listOf())
  }

  fun setPokemonWeaknesses(weakness: MutableList<WeaknessesResponse>) {
    this.pokemonWeaknesses.addAll(0, listOf(weakness))
  }

  fun getPokemonWeakness() = this.pokemonWeaknesses

  companion object {
    @BindingAdapter("app:bindPokemonWeaknesses")
    @JvmStatic
    fun bindPokemonWeaknesses(
      rcvPokemonWeakness: RecyclerView,
      pokemonWeakness: MutableList<WeaknessesResponse>?
    ) {
      if (rcvPokemonWeakness.adapter == null) {
        rcvPokemonWeakness.adapter = PokemonWeaknessAdapter()
      }

      (rcvPokemonWeakness.adapter as PokemonWeaknessAdapter).updatePokemonWeaknessList(
        pokemonWeakness
      )
    }
  }

}