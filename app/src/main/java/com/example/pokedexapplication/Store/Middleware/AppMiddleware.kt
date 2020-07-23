package com.example.pokedexapplication.Store.Middleware

import android.os.Looper
import android.util.Log
import com.example.pokedexapplication.Model.ListPokemonResponse
import com.example.pokedexapplication.Store.Action.AppAction
import com.example.pokedexapplication.Store.Action.PokemonAction
import com.example.pokedexapplication.Store.State.RootState
import com.example.pokedexapplication.adapter.ViewPager.MainViewPagerAdapter
import com.example.pokedexapplication.adapter.ViewPager.MainViewPagerAdapter.Companion.MAIN_NAV_TABS
import com.example.pokedexapplication.store
import org.rekotlin.DispatchFunction
import org.rekotlin.Middleware
import java.util.logging.Handler

internal val appMiddleware: Middleware<RootState> = { dispatch, _ ->
  { next ->
    { action ->
      run {
        when (action) {
          is AppAction.UPDATE_IS_SEARCHING -> {
            if (!action.isSearching) {
              resetListSearch(dispatch)
            } else {
              startSearching(dispatch)
            }
          }
        }

        next(action)
      }
    }
  }
}

fun startSearching(dispatch: DispatchFunction) {
  store.state.apply {
    if (appState.searchQuery != appState.lastSearchQuery) {
      resetListSearch(dispatch)
    }

    android.os.Handler((Looper.getMainLooper())).postDelayed({
      when (appState.titleMainActivity) {
        MAIN_NAV_TABS[0].title -> {
          Log.d("Binh", "Search page: ${pokemonState.searchPokemons.size / 20 + 1}")

          dispatch(
            PokemonAction.FETCH_DATA(
              isSearching = true,
              name = appState.searchQuery,
              page = pokemonState.searchPokemons.size / 20 + 1
            )
          )
        }
      }
    }, 100)
  }

}


fun resetListSearch(dispatch: DispatchFunction) {
  dispatch(
    PokemonAction.UPDATE_SEARCH_POKEMONS_STATE(
      ListPokemonResponse(
        pokemons = mutableListOf(),
        total = store.state.pokemonState.searchTotal
      )
    )
  )

}
