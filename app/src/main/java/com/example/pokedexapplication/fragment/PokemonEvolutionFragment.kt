package com.example.pokedexapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.pokedexapplication.R
import com.example.pokedexapplication.Store.State.PokemonState
import com.example.pokedexapplication.databinding.FragmentPokemonEvolutionBinding
import com.example.pokedexapplication.databinding.FragmentPokemonMoveBinding
import com.example.pokedexapplication.store
import com.example.pokedexapplication.viewModel.PokemonDetailViewModel
import org.rekotlin.StoreSubscriber

class PokemonEvolutionFragment : Fragment(), StoreSubscriber<PokemonState> {
  private lateinit var mFragmentPokemonEvolutionBinding: FragmentPokemonEvolutionBinding

  private lateinit var mPokemonDetailViewModel: PokemonDetailViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    mFragmentPokemonEvolutionBinding = DataBindingUtil.inflate(
      inflater,
      R.layout.fragment_pokemon_evolution,
      container,
      false
    )

    mPokemonDetailViewModel = PokemonDetailViewModel()

    mFragmentPokemonEvolutionBinding.mPokemonDetailViewModel = mPokemonDetailViewModel

    return mFragmentPokemonEvolutionBinding.root
  }

  override fun newState(state: PokemonState) {
    state.apply {
      mPokemonDetailViewModel.setIsLoading(isLoadingPokemonDetail)
      mPokemonDetailViewModel.setPokemonDetail(pokemonDetail)
    }
  }

  override fun onStart() {
    super.onStart()

    store.subscribe(this) {
      it.select { it ->
        it.pokemonState
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()

    store.unsubscribe(this)
  }
}