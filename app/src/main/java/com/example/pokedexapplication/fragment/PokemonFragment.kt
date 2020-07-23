package com.example.pokedexapplication.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.pokedexapplication.R
import com.example.pokedexapplication.Store.Action.PokemonAction
import com.example.pokedexapplication.Store.State.PokemonState
import com.example.pokedexapplication.adapter.RecyclerView.PokemonItemAdapter
import com.example.pokedexapplication.databinding.FragmentPokemonBinding
import com.example.pokedexapplication.dialog.PokemonShortInformationDialog
import com.example.pokedexapplication.store
import com.example.pokedexapplication.viewModel.PokemonFragmentViewModel
import org.rekotlin.StoreSubscriber


class PokemonFragment :
  Fragment(),
  StoreSubscriber<PokemonState>,
  PokemonItemAdapter.PokemonItemEventListener,
  PokemonShortInformationDialog.IInformationPokemonDialogOnDimissListener {

  private lateinit var mFragmentPokemonBinding: FragmentPokemonBinding

  private lateinit var mPokemonFragmentViewModel: PokemonFragmentViewModel

  private lateinit var mLinearLayoutManager: LinearLayoutManager

  private lateinit var mPokemonItemAdapter: PokemonItemAdapter

  private lateinit var mPokemonDialog: PokemonShortInformationDialog

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

    mPokemonItemAdapter = PokemonItemAdapter(this)

    mFragmentPokemonBinding.rcvPokemon.adapter = mPokemonItemAdapter

    mPokemonDialog = PokemonShortInformationDialog(activity as AppCompatActivity, this)

    eventListener()

    return mFragmentPokemonBinding.pokemonFragment
  }

  private fun eventListener() {
    mFragmentPokemonBinding.rcvPokemon.addOnScrollListener(object : OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (!recyclerView.canScrollVertically(1)) {
          store.state.appState.let { appState ->
            store.state.pokemonState.apply {
              val listSize =
                if (appState.isSearching) searchPokemons.size else pokemons.size
              val total = if (appState.isSearching) searchTotal else total

              if (listSize >= total) {
                Toast.makeText(activity, "No pokemon more...", Toast.LENGTH_SHORT).show()
              } else {
                store.dispatch(
                  PokemonAction.FETCH_DATA(
                    name = if (appState.isSearching) appState.searchQuery else "",
                    page = listSize / 20 + 1,
                    isSearching = appState.isSearching
                  )
                )
              }
            }
          }

        }
      }
    })
  }

  override fun newState(state: PokemonState) {
    state.apply {
      mPokemonFragmentViewModel.setPokemonList(pokemons)

      mPokemonFragmentViewModel.setSearchPokemonList(searchPokemons)
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

    Log.d("Binh", "Destroy ${store.state.pokemonState.pokemons.size}")

    store.unsubscribe(this)
  }

  override fun onItemClickListener(position: Int, pokemonId: String) {
    mPokemonItemAdapter.updateIndexSelected(position)
    mPokemonDialog.showDialog(position, pokemonId)
  }

  override fun onDimissListener(positionPokemon: Int) {
    mPokemonItemAdapter.updateIndexSelected(positionPokemon)
  }

}