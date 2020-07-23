package com.example.pokedexapplication.Store.Reducer

import android.util.Log
import com.example.pokedexapplication.Store.Action.AppAction
import com.example.pokedexapplication.Store.State.AppState
import com.example.pokedexapplication.Store.State.FirstFetchData
import org.rekotlin.Action

fun appReducer(action: Action, appState: AppState?): AppState {
  var _appState = appState ?: AppState()

  when (action) {
    is AppAction.UPDATE_IS_FETCHING -> {
      _appState = _appState.copy(isFetching = action.isFetching)
    }

    is AppAction.UPDATE_IS_SEARCHING -> {
      _appState = _appState.copy(isSearching = action.isSearching)
    }

    is AppAction.UPDATE_FIRST_FETCH -> {
      var _isFirstFetchDataPayload = action.isFirstFetchData

      _appState = _appState.copy(
        isFirstFetchData = FirstFetchData(
          isItemFirstFetch = _isFirstFetchDataPayload.isItemFirstFetch || _appState.isFirstFetchData.isItemFirstFetch,
          isMoveFirstFetch = _isFirstFetchDataPayload.isMoveFirstFetch || _appState.isFirstFetchData.isMoveFirstFetch,
          isPokemonFirstFetch = _isFirstFetchDataPayload.isPokemonFirstFetch || _appState.isFirstFetchData.isPokemonFirstFetch
        )
      )
    }

    is AppAction.UPDATE_TITLE_MAIN_ACTIVITY -> {
      Log.d("Binh", action.title)

      _appState = _appState.copy(
        titleMainActivity = action.title
      )
    }

    is AppAction.UPDATE_SEARCH_QUERY -> {
      _appState = _appState.copy(
        searchQuery = action.query
      )
    }

    is AppAction.UPDATE_LAST_SEARCH_QUERY -> {
      _appState = _appState.copy(
        lastSearchQuery = action.query
      )
    }
  }

  return _appState
}