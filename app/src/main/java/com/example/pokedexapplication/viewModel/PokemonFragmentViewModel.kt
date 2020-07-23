package com.example.pokedexapplication.viewModel

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapplication.adapter.RecyclerView.PokemonItemAdapter
import com.example.pokedexapplication.Model.Pokemon

class PokemonFragmentViewModel() : BaseObservable() {
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
    fun setBindPokemonData(rcvPokemon: RecyclerView, pokemons: ObservableArrayList<MutableList<Pokemon>>) {
      if (rcvPokemon.adapter == null) {
        Log.d("Binh", "Model1: ${pokemons[0].size}")
        rcvPokemon.adapter = PokemonItemAdapter(pokemonList = pokemons[0])
      } else {
        Log.d("Binh", "Model: ${pokemons[0].size}")
        (rcvPokemon.adapter as PokemonItemAdapter).updatePokemonList(pokemons[0])
      }
    }
  }


}