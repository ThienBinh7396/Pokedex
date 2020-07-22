package com.example.pokedexapplication.Store.Middleware

import android.util.Log
import com.example.pokedexapplication.Model.API.APIUtils
import com.example.pokedexapplication.Model.ListPokemonResponse
import com.example.pokedexapplication.Store.Action.AppAction
import com.example.pokedexapplication.Store.Action.PokemonAction
import com.example.pokedexapplication.Store.State.FirstFetchData
import com.example.pokedexapplication.Store.State.RootState
import org.rekotlin.DispatchFunction
import org.rekotlin.Middleware
import retrofit2.Call
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
  APIUtils.getAPIService().fetchPokemons(
    name = name,
    page = page
  ).enqueue(object : retrofit2.Callback<ListPokemonResponse> {
    override fun onFailure(call: Call<ListPokemonResponse>, t: Throwable) {
      Log.d("Binh", "Fetch pokemon data faild!!!")
    }

    override fun onResponse(
      call: Call<ListPokemonResponse>,
      response: Response<ListPokemonResponse>
    ) {
      if (!isSearching) dispatch(PokemonAction.UPDATE_STATE(response.body()!!))

      if (isFirstFetch) dispatch(
        AppAction.UPDATE_FIRST_FETCH(
          FirstFetchData(
            isPokemonFirstFetch = true
          )
        )
      )
    }
  })
}