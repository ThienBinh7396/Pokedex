package com.example.pokedexapplication.adapter.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapplication.model.Pokemon
import com.example.pokedexapplication.R
import com.example.pokedexapplication.databinding.PokemonItemBinding
import com.example.pokedexapplication.viewModel.PokemonViewModel


class PokemonItemAdapter(var eventLintener: PokemonItemEventListener) :
  RecyclerView.Adapter<PokemonItemAdapter.PokemonItemViewHolder>() {
  private var pokemonList: MutableList<Pokemon> = mutableListOf()

  private var currentIndexSelected = -1

  class PokemonItemViewHolder(
    var mPokemonItemBinding: PokemonItemBinding,
    eventLintener: PokemonItemEventListener
  ) :
    RecyclerView.ViewHolder(
      mPokemonItemBinding.pokemonItem
    ) {
    private var mLinearLayoutManager = LinearLayoutManager(mPokemonItemBinding.pokemonItem.context)

    init {
      mLinearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

      mPokemonItemBinding.rcvPokemonType.layoutManager = mLinearLayoutManager

      mPokemonItemBinding.mPokemonItemEventListener = eventLintener
    }

    fun bindPokemonItem(pokemonItem: Pokemon, position: Int) {
      if (mPokemonItemBinding.mPokemonViewModel == null) {
        mPokemonItemBinding.mPokemonViewModel = PokemonViewModel(pokemonItem, position)
      } else {
        (mPokemonItemBinding.mPokemonViewModel as PokemonViewModel).setPokemonData(pokemonItem)
      }
    }

  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonItemViewHolder {
    var mPokemonItemBinding = DataBindingUtil.inflate<PokemonItemBinding>(
      LayoutInflater.from(parent.context), R.layout.pokemon_item, parent, false
    )

    return PokemonItemViewHolder(mPokemonItemBinding, eventLintener)
  }

  override fun getItemCount() = pokemonList.size

  override fun onBindViewHolder(holder: PokemonItemViewHolder, position: Int) {
    holder.bindPokemonItem(pokemonList[position], position)
  }

  fun updatePokemonList(_pokemonList: MutableList<Pokemon>, refresh: Boolean = true) {

    val diffCallback = PokemonDiffCallback(pokemonList, _pokemonList)
    val diffResult = DiffUtil.calculateDiff(diffCallback)

    if (refresh) pokemonList.clear()
    pokemonList.addAll(_pokemonList.toList())

    diffResult.dispatchUpdatesTo(this)
  }

  fun updateIndexSelected(index: Int) {
    if (this.currentIndexSelected != -1) {
      this.pokemonList[this.currentIndexSelected].isSelected = false
      notifyItemChanged(this.currentIndexSelected)
    }

    this.pokemonList[index].isSelected = this.currentIndexSelected != index
    this.currentIndexSelected = if (this.currentIndexSelected == index) -1 else index

    notifyItemChanged(index)
  }

  override fun getItemViewType(position: Int): Int {
    return position
  }

  interface PokemonItemEventListener {
    fun onItemClickListener(position: Int, pokemonId: String)
  }

  class PokemonDiffCallback(var oldList: MutableList<Pokemon>, var newList: MutableList<Pokemon>) :
    DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
      oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
      oldList[oldItemPosition].id == newList[newItemPosition].id
  }
}
