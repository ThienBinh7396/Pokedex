package com.example.pokedexapplication.adapter.RecyclerView

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapplication.GlideApp
import com.example.pokedexapplication.R
import com.example.pokedexapplication.common.PokemonTypeUtils.Companion.POKEMON_TYPE

class PokemonTypeItemAdapter :
  RecyclerView.Adapter<PokemonTypeItemAdapter.PokemonTypeViewHolder>() {

  private var typesOfPokemon: MutableList<String> = mutableListOf()

  class PokemonTypeViewHolder(var view: View) :
    RecyclerView.ViewHolder(view) {
    private val imvPokemonType = view.findViewById<ImageView>(R.id.imvPokemonType)

    fun bindPokemonTypeItem(pokemonType: String) {
      GlideApp.with(view.context)
        .load(POKEMON_TYPE[pokemonType])
        .fitCenter()
        .into(imvPokemonType)
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    PokemonTypeViewHolder(
      LayoutInflater.from(parent.context)
        .inflate(R.layout.pokemon_type_item, parent, false)
    )

  override fun getItemCount(): Int = typesOfPokemon.size

  override fun onBindViewHolder(holder: PokemonTypeViewHolder, position: Int) {
    holder.bindPokemonTypeItem(typesOfPokemon[position])
  }

  fun updatePokemonTypes(_pokemonTypes: MutableList<String>?, refresh: Boolean = true) {
    if(_pokemonTypes == null) return

    if (refresh) typesOfPokemon.clear()
    typesOfPokemon.addAll(_pokemonTypes)
    notifyDataSetChanged()
  }

  override fun getItemViewType(position: Int): Int {
    return position
  }


}