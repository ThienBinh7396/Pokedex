package com.example.pokedexapplication.Store.Middleware

import android.os.Looper
import android.util.Log
import com.example.pokedexapplication.model.ListItemResponse
import com.example.pokedexapplication.model.ListMoveResponse
import com.example.pokedexapplication.model.ListPokemonResponse
import com.example.pokedexapplication.Store.Action.AppAction
import com.example.pokedexapplication.Store.Action.ItemAction
import com.example.pokedexapplication.Store.Action.MoveAction
import com.example.pokedexapplication.Store.Action.PokemonAction
import com.example.pokedexapplication.Store.State.RootState
import com.example.pokedexapplication.adapter.viewPager.MainViewPagerAdapter.Companion.MAIN_NAV_TABS
import com.example.pokedexapplication.store
import org.rekotlin.DispatchFunction
import org.rekotlin.Middleware

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

        MAIN_NAV_TABS[1].title -> {
          Log.d("Binh", "Search move page: ${moveState.searchMoves.size / 20 + 1}")

          dispatch(
            MoveAction.FETCH_DATA(
              isSearching = true,
              name = appState.searchQuery,
              page = moveState.searchMoves.size / 20 + 1
            )
          )
        }

        MAIN_NAV_TABS[2].title -> {
          Log.d("Binh", "Search item page: ${itemState.searchItems.size / 20 + 1}")

          dispatch(
            ItemAction.FETCH_DATA(
              isSearching = true,
              name = appState.searchQuery,
              page = itemState.searchItems.size / 20 + 1
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

  dispatch(
    MoveAction.UPDATE_SEARCH_MOVES_STATE(
      ListMoveResponse(
        moves = mutableListOf(),
        total = store.state.moveState.searchTotal
      )
    )
  )

  dispatch(
    ItemAction.UPDATE_SEARCH_ITEM_STATE(
      ListItemResponse(
        items = mutableListOf(),
        total = store.state.itemState.searchTotal
      )
    )
  )
}
