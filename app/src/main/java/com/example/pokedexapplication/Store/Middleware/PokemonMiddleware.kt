package com.example.pokedexapplication.Store.Middleware

import android.util.Log
import com.example.pokedexapplication.Model.API.APIUtils
import com.example.pokedexapplication.Model.ListPokemonResponse
import com.example.pokedexapplication.Model.PokemonDetail
import com.example.pokedexapplication.Store.Action.AppAction
import com.example.pokedexapplication.Store.Action.PokemonAction
import com.example.pokedexapplication.Store.State.FirstFetchData
import com.example.pokedexapplication.Store.State.RootState
import com.example.pokedexapplication.store
import org.rekotlin.DispatchFunction
import org.rekotlin.Middleware
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal val pokemonMiddleware: Middleware<RootState> = { dispatch, _ ->
  { next ->
    { action ->
      run {
        when (action) {
          is PokemonAction.FETCH_DATA -> {
            fetchPokemonsData(
              action.page,
              action.name,
              action.isSearching,
              action.isFirstFetch,
              dispatch
            )
          }
          is PokemonAction.FETCH_POKEMON_DETAIL -> {
            fetchPokemonDetailById(action.id, dispatch)
          }
        }

        next(action)
      }
    }
  }
}

fun fetchPokemonsData(
  page: Int?,
  name: String,
  isSearching: Boolean,
  isFirstFetch: Boolean,
  dispatch: DispatchFunction
) {
  store.state.pokemonState.apply {
    if (!isSearching && total != 0 && pokemons.size >= total) {
      return@fetchPokemonsData
    }

    if (isSearching && searchTotal != 0 &&  searchPokemons.size >= searchTotal) {
      return@fetchPokemonsData
    }
  }

  dispatch(PokemonAction.UPDATE_POKEMONS_LOADING_STATE(true))

  APIUtils.getAPIService().fetchPokemons(
    name = name,
    page = page
  ).enqueue(object : Callback<ListPokemonResponse> {
    override fun onFailure(call: Call<ListPokemonResponse>, t: Throwable) {
      Log.d("Binh", "Fetch pokemon data faild!!!")
      dispatch(PokemonAction.UPDATE_POKEMONS_LOADING_STATE(false))
    }

    override fun onResponse(
      call: Call<ListPokemonResponse>,
      response: Response<ListPokemonResponse>
    ) {
      Log.d("Binh", "List pokemons: ${response.body()!!.pokemons.size}, ${response.body()!!.total}")
      var total = response.body()!!.total
      var pokemons = response.body()!!.pokemons

      dispatch(PokemonAction.UPDATE_POKEMONS_LOADING_STATE(false))

      if (isFirstFetch) {
        dispatch(
          AppAction.UPDATE_FIRST_FETCH(
            FirstFetchData(
              isPokemonFirstFetch = true
            )
          )
        )

      }

      if (!isSearching) {
        if (!isFirstFetch) {
          pokemons.addAll(0, store.state.pokemonState.pokemons)
        }

        dispatch(
          PokemonAction.UPDATE_STATE(
            ListPokemonResponse(pokemons, total)
          )
        )
      }

      if (isSearching) {
        pokemons.addAll(0, store.state.pokemonState.searchPokemons)
        dispatch(
          PokemonAction.UPDATE_SEARCH_POKEMONS_STATE(
            ListPokemonResponse(pokemons, total)
          )
        )
      }
    }
  })
}

fun fetchPokemonDetailById(id: String, dispatch: DispatchFunction) {
  dispatch(PokemonAction.UPDATE_DETAIL_LOADING_STATE(true))

  APIUtils.getAPIService().getDetailPokemonById(id).enqueue(object : Callback<PokemonDetail> {
    override fun onFailure(call: Call<PokemonDetail>, t: Throwable) {
      dispatch(PokemonAction.UPDATE_DETAIL_LOADING_STATE(false))

      Log.d("Binh", "Faild ..........................")
    }

    override fun onResponse(call: Call<PokemonDetail>, response: Response<PokemonDetail>) {
      dispatch(PokemonAction.UPDATE_DETAIL_LOADING_STATE(false))
      dispatch(PokemonAction.UPDATE_POKEMON_DETAIL(response.body()))
      Log.d("Binh", "Response pokemon: ${(response.body() as PokemonDetail).stats.weaknesses}")
    }

  })
}
