package com.example.pokedexapplication.adapter.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapplication.model.WeaknessesResponse
import com.example.pokedexapplication.R
import com.example.pokedexapplication.databinding.PokemonWeaknessBinding
import com.example.pokedexapplication.viewModel.PokemonWeaknessViewModel

class PokemonWeaknessAdapter :
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
    if (pokemonWeaknessList == null) return

    val diffCallback = PokemonWeaknessDiffCallback(mPokemonWeaknessList, pokemonWeaknessList)

    val diffResult = DiffUtil.calculateDiff(diffCallback)

    if (refresh) mPokemonWeaknessList.clear()

    mPokemonWeaknessList.addAll(pokemonWeaknessList)

    diffResult.dispatchUpdatesTo(this)
  }

  class PokemonWeaknessDiffCallback(
    var oldList: MutableList<WeaknessesResponse>,
    var newList: MutableList<WeaknessesResponse>
  ) :
    DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
      oldList[oldItemPosition].pokemonType == newList[newItemPosition].pokemonType

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
      oldList[oldItemPosition].pokemonType == newList[newItemPosition].pokemonType

  }
}