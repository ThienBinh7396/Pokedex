package com.example.pokedexapplication.viewModel

import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapplication.adapter.RecyclerView.PokemonTypeItemAdapter

class PokemonTypeViewModel: BaseObservable() {
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

  companion object{
   @BindingAdapter("app:bindPokemonTypes")
   @JvmStatic
   fun bindPokemonTypes(rcvPokemonType: RecyclerView, pokemonTypes: MutableList<String>? = mutableListOf()){
     if (rcvPokemonType.adapter == null){
       rcvPokemonType.adapter = PokemonTypeItemAdapter()
     }

     (rcvPokemonType.adapter as PokemonTypeItemAdapter).updatePokemonTypes(pokemonTypes)
   }
  }

}