package com.example.pokedexapplication.viewModel

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapplication.adapter.RecyclerView.PokemonItemAdapter
import com.example.pokedexapplication.Model.Pokemon
import com.example.pokedexapplication.store

class PokemonFragmentViewModel : BaseObservable() {
  private var mPokemonList = ObservableArrayList<MutableList<Pokemon>>()

  private var mSearchPokemonList = ObservableArrayList<MutableList<Pokemon>>()

  private var isLoading = false

  fun setPokemonList(mPokemonList: MutableList<Pokemon>) {
    this.mPokemonList.addAll(0, listOf(mPokemonList))
    notifyChange()
  }

  fun getPokemonList() = this.mPokemonList

  fun setSearchPokemonList(mSearchPokemonList: MutableList<Pokemon>) {
    this.mSearchPokemonList.addAll(0, listOf(mSearchPokemonList))
    notifyChange()
  }

  fun getSearchPokemonList() = this.mSearchPokemonList

  fun setIsLoading(isLoading: Boolean) {
    this.isLoading = isLoading
    notifyChange()
  }

  fun getIsLoading() = this.isLoading

  fun getTypeOfPokemonsIsSearchList() = store.state.appState.isSearching

  companion object {
    @BindingAdapter("app:bindPokemonData")
    @JvmStatic
    fun setBindPokemonData(
      rcvPokemon: RecyclerView,
      pokemons: ObservableArrayList<MutableList<Pokemon>>
    ) {
      if (rcvPokemon.adapter != null) {
        (rcvPokemon.adapter as PokemonItemAdapter).updatePokemonList(pokemons[0])
      }
    }
  }


}