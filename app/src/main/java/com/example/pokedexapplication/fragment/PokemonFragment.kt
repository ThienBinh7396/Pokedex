package com.example.pokedexapplication.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedexapplication.R
import com.example.pokedexapplication.Store.State.PokemonState
import com.example.pokedexapplication.databinding.FragmentPokemonBinding
import com.example.pokedexapplication.databinding.PokemonItemBinding
import com.example.pokedexapplication.store
import com.example.pokedexapplication.viewModel.PokemonFragmentViewModel
import org.rekotlin.StoreSubscriber

class PokemonFragment : Fragment(), StoreSubscriber<PokemonState> {

  private lateinit var mFragmentPokemonBinding: FragmentPokemonBinding

  private lateinit var mPokemonFragmentViewModel: PokemonFragmentViewModel

  private lateinit var mLinearLayoutManager: LinearLayoutManager

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    mFragmentPokemonBinding = DataBindingUtil.inflate<FragmentPokemonBinding>(
      inflater,
      R.layout.fragment_pokemon,
      container,
      false
    )

    mPokemonFragmentViewModel = PokemonFragmentViewModel()

    mFragmentPokemonBinding.mPokemonFragmentViewModel = mPokemonFragmentViewModel

    mLinearLayoutManager = LinearLayoutManager(context)
    mLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL

    mFragmentPokemonBinding.rcvPokemon.layoutManager = mLinearLayoutManager

    return mFragmentPokemonBinding.pokemonFragment
  }

  override fun newState(state: PokemonState) {
    state.apply {
      Log.d("Binh", "Total: ${pokemons.size}")
      mPokemonFragmentViewModel.setPokemonList(pokemons)
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