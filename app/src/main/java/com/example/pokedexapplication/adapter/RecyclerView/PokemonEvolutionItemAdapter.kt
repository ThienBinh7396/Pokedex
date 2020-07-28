package com.example.pokedexapplication.adapter.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapplication.model.PokemonEvolution
import com.example.pokedexapplication.R
import com.example.pokedexapplication.databinding.EvolutionPokemonItemBinding
import com.example.pokedexapplication.viewModel.PokemonEvolutionViewModel

class PokemonEvolutionItemAdapter :
  RecyclerView.Adapter<PokemonEvolutionItemAdapter.PokemonEvolutionItemViewHolder>() {
  private var mPokemonEvolution = mutableListOf<PokemonEvolution>()

  class PokemonEvolutionItemViewHolder(var mEvolutionPokemonItemBinding: EvolutionPokemonItemBinding) :
    RecyclerView.ViewHolder(mEvolutionPokemonItemBinding.root) {

    fun bindData(pokemonEvolution: PokemonEvolution) {
      if (mEvolutionPokemonItemBinding.mPokemonEvolutionViewModel == null) {
        mEvolutionPokemonItemBinding.mPokemonEvolutionViewModel =
          PokemonEvolutionViewModel(pokemonEvolution)
      } else {
        (mEvolutionPokemonItemBinding.mPokemonEvolutionViewModel as PokemonEvolutionViewModel).setPokemonEvoluation(
          pokemonEvolution
        )
      }
    }
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): PokemonEvolutionItemViewHolder = PokemonEvolutionItemViewHolder(
    DataBindingUtil.inflate(
      LayoutInflater.from(parent.context),
      R.layout.evolution_pokemon_item,
      parent,
      false
    )
  )

  override fun getItemCount(): Int = mPokemonEvolution.size

  override fun onBindViewHolder(holder: PokemonEvolutionItemViewHolder, position: Int) {
    holder.bindData(mPokemonEvolution[position])
  }

  fun updatePokemonEvolutions(newList: MutableList<PokemonEvolution>) {
    val diffCallback = PokemonEvolutionCallback(mPokemonEvolution, newList)
    val diffResult = DiffUtil.calculateDiff(diffCallback)

    mPokemonEvolution.clear()

    mPokemonEvolution.addAll(newList)

    diffResult.dispatchUpdatesTo(this)
  }

  class PokemonEvolutionCallback(
    var oldList: MutableList<PokemonEvolution>,
    var newList: MutableList<PokemonEvolution>
  ) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
      oldList[oldItemPosition].pokemonName == newList[newItemPosition].pokemonName
        && oldList[oldItemPosition].evolutionPokemonName == newList[newItemPosition].evolutionPokemonName

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
      oldList[oldItemPosition].pokemonName == newList[newItemPosition].pokemonName
        && oldList[oldItemPosition].evolutionPokemonName == newList[newItemPosition].evolutionPokemonName

  }
}