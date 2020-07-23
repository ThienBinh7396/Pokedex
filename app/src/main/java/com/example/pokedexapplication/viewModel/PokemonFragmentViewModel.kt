package com.example.pokedexapplication.viewModel

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapplication.adapter.RecyclerView.PokemonItemAdapter
import com.example.pokedexapplication.Model.Pokemon

class PokemonFragmentViewModel : BaseObservable() {
  private var mPokemonList = ObservableArrayList<MutableList<Pokemon>>()

  fun setPokemonList(mPokemonList: MutableList<Pokemon>) {
    this.mPokemonList.addAll(0, listOf(mPokemonList))
    notifyChange()
  }

  fun getPokemonList(): ObservableArrayList<MutableList<Pokemon>>? {
    return this.mPokemonList
  }

  companion object {
    @BindingAdapter("app:bindPokemonData")
    @JvmStatic
    fun setBindPokemonData(
      rcvPokemon: RecyclerView,
      pokemons: ObservableArrayList<MutableList<Pokemon>>
    ) {
      val adapter =
        if (rcvPokemon.adapter == null) PokemonItemAdapter() else (rcvPokemon.adapter as PokemonItemAdapter)

      if (rcvPokemon.adapter  == null) {
        rcvPokemon.adapter = adapter
      }
      adapter.updatePokemonList(pokemons[0])
    }
  }


}