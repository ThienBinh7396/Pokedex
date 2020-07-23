package com.example.pokedexapplication.viewModel

import android.util.Log
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapplication.Model.Pokemon
import com.example.pokedexapplication.R
import com.example.pokedexapplication.adapter.RecyclerView.PokemonTypeItemAdapter

class PokemonViewModel(pokemon: Pokemon, position: Int) : BaseObservable() {
  private var pokemon: Pokemon? = null

  private var pokemonPosition: Int = 0

  init {
    this.pokemon = pokemon
    this.pokemonPosition = position
  }

  val id
    get() = this.pokemon!!.id
  val idText
    get() = "#${this.pokemon!!.id}"
  val name
    get() = this.pokemon!!.name
  val description
    get() = this.pokemon!!.description
  val image
    get() = this.pokemon!!.image
  val pokemonTypes
    get() = this.pokemon!!.pokemonTypes
  val isSelected
    get() = this.pokemon!!.isSelected
  val position
    get() = this.pokemonPosition

  fun setPokemonData(pokemon: Pokemon) {
    this.pokemon = pokemon
    notifyChange()
  }

  companion object {
    @BindingAdapter("app:bindPokemonTypeData")
    @JvmStatic
    fun bindPokemonTypeData(
      rcvPokemonType: RecyclerView,
      pokemonTypes: ObservableArrayList<MutableList<String>>
    ) {
      if (rcvPokemonType.adapter == null) {
        rcvPokemonType.adapter = PokemonTypeItemAdapter()
      } else {
        (rcvPokemonType.adapter as PokemonTypeItemAdapter).updatePokemonTypes(pokemonTypes[0])
      }
    }

    @BindingAdapter("app:bindPokemonSelectedBackground")
    @JvmStatic
    fun bindPokemonSelectedBackground(view: View, isSelected: Boolean) {
      view.setBackgroundResource(
        if (isSelected) R.drawable.pokemon_item_selected_background
        else R.color.colorTransparent
      )
    }
  }
}