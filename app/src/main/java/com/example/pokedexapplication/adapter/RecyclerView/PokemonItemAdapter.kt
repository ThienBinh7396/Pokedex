package com.example.pokedexapplication.adapter.RecyclerView

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapplication.Model.Pokemon
import com.example.pokedexapplication.R
import com.example.pokedexapplication.databinding.FragmentPokemonBinding
import com.example.pokedexapplication.databinding.PokemonItemBinding
import com.example.pokedexapplication.databinding.PokemonTypeItemBinding
import com.example.pokedexapplication.viewModel.PokemonFragmentViewModel
import com.example.pokedexapplication.viewModel.PokemonTypeViewModel
import com.example.pokedexapplication.viewModel.PokemonViewModel

class PokemonItemAdapter :
  RecyclerView.Adapter<PokemonItemAdapter.PokemonItemViewHolder>() {
  private var pokemonList: MutableList<Pokemon> = mutableListOf()

  class PokemonItemViewHolder(var mPokemonItemBinding: PokemonItemBinding) :
    RecyclerView.ViewHolder(
      mPokemonItemBinding.pokemonItem
    ) {
    private var mLinearLayoutManager = LinearLayoutManager(mPokemonItemBinding.pokemonItem.context)

    private var mPokemonTypeItemAdapter = PokemonTypeItemAdapter()

    init {
      mLinearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

      mPokemonItemBinding.rcvPokemonType.adapter = mPokemonTypeItemAdapter
      mPokemonItemBinding.rcvPokemonType.layoutManager = mLinearLayoutManager
    }

    fun bindPokemonItem(pokemonItem: Pokemon) {
      if (mPokemonItemBinding.mPokemonViewModel == null) {
        mPokemonItemBinding.mPokemonViewModel = PokemonViewModel(pokemonItem)
      } else {
        (mPokemonItemBinding.mPokemonViewModel as PokemonViewModel).setPokemonData(pokemonItem)
      }

      mPokemonTypeItemAdapter.updatePokemonTypes(pokemonItem.pokemonTypes)
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonItemViewHolder {
    var mPokemonItemBinding = DataBindingUtil.inflate<PokemonItemBinding>(
      LayoutInflater.from(parent.context), R.layout.pokemon_item, parent, false
    )

    return PokemonItemViewHolder(mPokemonItemBinding)
  }

  override fun getItemCount() = pokemonList.size

  override fun onBindViewHolder(holder: PokemonItemViewHolder, position: Int) {
    holder.bindPokemonItem(pokemonList[position])
  }

  fun updatePokemonList(_pokemonList: MutableList<Pokemon>, refresh: Boolean = true) {
    if (refresh) pokemonList.clear()
    pokemonList.addAll(_pokemonList.toList())
    notifyDataSetChanged()
  }

  override fun getItemViewType(position: Int): Int {
    return position
  }
}
