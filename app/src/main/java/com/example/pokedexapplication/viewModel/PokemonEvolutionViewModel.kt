package com.example.pokedexapplication.viewModel

import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapplication.model.PokemonEvolution
import com.example.pokedexapplication.adapter.RecyclerView.PokemonEvolutionItemAdapter

class PokemonEvolutionViewModel(pokemonEvolution: PokemonEvolution) : BaseObservable() {
  private var mPokemonEvolution = pokemonEvolution

  val pokemonEvolution
    get() = this.mPokemonEvolution

  val evolutionLevelText
    get() = "Lv.${this.mPokemonEvolution.evolutionLevel}"

  fun setPokemonEvoluation(pokemonEvolution: PokemonEvolution) {
    this.mPokemonEvolution = pokemonEvolution
    notifyChange()
  }


  companion object {
    @BindingAdapter("app:bindEvolutionsList")
    @JvmStatic
    fun bindEvolutionsList(
      rcvEvolutions: RecyclerView,
      evolutions: MutableList<PokemonEvolution>?
    ) {
      if (rcvEvolutions.adapter == null) {
        rcvEvolutions.adapter = PokemonEvolutionItemAdapter()
        rcvEvolutions.layoutManager = LinearLayoutManager(rcvEvolutions.context)
        (rcvEvolutions.layoutManager as LinearLayoutManager).orientation =
          LinearLayoutManager.VERTICAL
      }

      if (evolutions != null) {
        (rcvEvolutions.adapter as PokemonEvolutionItemAdapter).updatePokemonEvolutions(evolutions)
      }
    }
  }
}