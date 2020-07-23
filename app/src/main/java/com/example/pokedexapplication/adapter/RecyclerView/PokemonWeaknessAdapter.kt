package com.example.pokedexapplication.adapter.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapplication.Model.WeaknessesResponse
import com.example.pokedexapplication.R
import com.example.pokedexapplication.databinding.PokemonWeaknessBinding
import com.example.pokedexapplication.viewModel.PokemonWeaknessViewModel

class PokemonWeaknessAdapter() :
  RecyclerView.Adapter<PokemonWeaknessAdapter.PokemonWeaknessViewHolder>() {

  private var mPokemonWeaknessList: MutableList<WeaknessesResponse> = mutableListOf()

  class PokemonWeaknessViewHolder(var pokemonWeaknessBinding: PokemonWeaknessBinding) :
    RecyclerView.ViewHolder(pokemonWeaknessBinding.root) {

    fun bindingData(data: WeaknessesResponse) {
      if (pokemonWeaknessBinding.mPokemonWeaknessViewModel == null) {
        pokemonWeaknessBinding.mPokemonWeaknessViewModel =
          PokemonWeaknessViewModel(data)
      } else {
        (pokemonWeaknessBinding.mPokemonWeaknessViewModel as PokemonWeaknessViewModel).setPokemonWeakness(
          data
        )
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonWeaknessViewHolder =
    PokemonWeaknessViewHolder(
      DataBindingUtil.inflate(
        LayoutInflater.from(parent.context),
        R.layout.pokemon_weakness,
        parent,
        false
      )
    )

  override fun getItemCount() = mPokemonWeaknessList.size

  override fun onBindViewHolder(holder: PokemonWeaknessViewHolder, position: Int) {
    holder.bindingData(mPokemonWeaknessList[position])
  }

  fun updatePokemonWeaknessList(
    pokemonWeaknessList: MutableList<WeaknessesResponse>?,
    refresh: Boolean = true
  ) {
    if(pokemonWeaknessList == null) return

    if (refresh) mPokemonWeaknessList.clear()

    mPokemonWeaknessList.addAll(pokemonWeaknessList)

    notifyDataSetChanged()
  }
}