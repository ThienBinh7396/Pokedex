package com.example.pokedexapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.pokedexapplication.R
import com.example.pokedexapplication.Store.State.PokemonState
import com.example.pokedexapplication.databinding.FragmentPokemonMoveBinding
import com.example.pokedexapplication.store
import com.example.pokedexapplication.viewModel.PokemonDetailViewModel
import org.rekotlin.StoreSubscriber

class PokemonMoveFragment : Fragment(), StoreSubscriber<PokemonState> {
  private lateinit var mFragmentPokemonMoveBinding: FragmentPokemonMoveBinding

  private lateinit var mPokemonDetailViewModel: PokemonDetailViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    mFragmentPokemonMoveBinding = DataBindingUtil.inflate(
      inflater,
      R.layout.fragment_pokemon_move,
      container,
      false
    )

    mPokemonDetailViewModel = PokemonDetailViewModel()

    mFragmentPokemonMoveBinding.mPokemonDetailViewModel = mPokemonDetailViewModel

    return mFragmentPokemonMoveBinding.root
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