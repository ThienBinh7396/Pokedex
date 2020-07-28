package com.example.pokedexapplication.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.pokedexapplication.R
import com.example.pokedexapplication.Store.State.PokemonState
import com.example.pokedexapplication.databinding.FragmentPokemonStatBinding
import com.example.pokedexapplication.store
import com.example.pokedexapplication.viewModel.PokemonDetailViewModel
import org.rekotlin.StoreSubscriber

class PokemonStatFragment : Fragment(), StoreSubscriber<PokemonState> {
  private lateinit var mFragmentPokemonStatBinding: FragmentPokemonStatBinding

  private lateinit var mPokemonDetailViewModel: PokemonDetailViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    mFragmentPokemonStatBinding = DataBindingUtil.inflate(
      inflater,
      R.layout.fragment_pokemon_stat,
      container,
      false
    )

    mPokemonDetailViewModel = PokemonDetailViewModel()

    mFragmentPokemonStatBinding.mPokemonDetailViewModel = mPokemonDetailViewModel

    return mFragmentPokemonStatBinding.root
  }

  override fun newState(state: PokemonState) {
   state.apply {
     mPokemonDetailViewModel.setPokemonDetail(pokemonDetail)
     mPokemonDetailViewModel.setIsLoading(isLoadingPokemonDetail)

     Log.d("Binh", "Stat fragment $isLoadingPokemonDetail")
   }
  }

  override fun onStart() {
    super.onStart()

    store.subscribe(this){
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