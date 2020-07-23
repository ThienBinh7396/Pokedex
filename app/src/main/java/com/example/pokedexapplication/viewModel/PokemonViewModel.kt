package com.example.pokedexapplication.viewModel

import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapplication.Model.Pokemon
import com.example.pokedexapplication.adapter.RecyclerView.PokemonTypeItemAdapter

class PokemonViewModel(pokemon: Pokemon) : BaseObservable() {
  private var pokemon: Pokemon? = null

  init {
    this.pokemon = pokemon
  }

  val id
    get() = "#${this.pokemon!!.id}"
  val name
    get() = this.pokemon!!.name
  val description
    get() = this.pokemon!!.description
  val image
    get() = this.pokemon!!.image

  val pokemonTypes
    get() = this.pokemon!!.pokemonTypes


  fun setPokemonData(pokemon: Pokemon) {
    this.pokemon = pokemon
    notifyChange()
  }

  companion object{
    @BindingAdapter("app:bindPokemonTypeData")
    @JvmStatic
    fun bindPokemonTypeData(rcvPokemonType: RecyclerView, pokemonTypes: ObservableArrayList<MutableList<String>>){
      if(rcvPokemonType.adapter == null){
        rcvPokemonType.adapter = PokemonTypeItemAdapter()
      }else{
        (rcvPokemonType.adapter as PokemonTypeItemAdapter).updatePokemonTypes(pokemonTypes[0])
      }
    }
  }
}