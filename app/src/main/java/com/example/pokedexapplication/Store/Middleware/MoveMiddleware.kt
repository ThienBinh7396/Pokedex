package com.example.pokedexapplication.Store.Middleware

import android.util.Log
import com.example.pokedexapplication.model.API.APIUtils
import com.example.pokedexapplication.model.ListMoveResponse
import com.example.pokedexapplication.Store.Action.AppAction
import com.example.pokedexapplication.Store.Action.MoveAction
import com.example.pokedexapplication.Store.State.FirstFetchData
import com.example.pokedexapplication.Store.State.RootState
import com.example.pokedexapplication.store
import org.rekotlin.DispatchFunction
import org.rekotlin.Middleware
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal val moveMiddleware: Middleware<RootState> = { dispatch, _ ->
  { next ->
    { action ->
      run {
        when (action) {
          is MoveAction.FETCH_DATA ->
            fetchMovesData(
              action.page,
              action.name,
              action.isSearching,
              action.isFirstFetch,
              dispatch
            )
        }
        next(action)
      }
    }
  }
}

fun fetchMovesData(
  page: Int?,
  name: String,
  isSearching: Boolean,
  isFirstFetch: Boolean,
  dispatch: DispatchFunction
) {
  store.state.moveState.apply {
    if (!isSearching && total != 0 && moves.size >= total) {
      return@fetchMovesData
    }

    if (isSearching && searchTotal != 0 && searchMoves.size >= searchTotal) {
      return@fetchMovesData
    }
  }

  dispatch(MoveAction.UPDATE_MOVES_LOADING_STATE(true))

  APIUtils.getAPIService().fetchMoves(
    page = page,
    name = name
  ).enqueue(object : Callback<ListMoveResponse> {
    override fun onFailure(call: Call<ListMoveResponse>, t: Throwable) {
      dispatch(MoveAction.UPDATE_MOVES_LOADING_STATE(false))
      Log.d("Binh", "Fetch move data faild!!!")
    }

    override fun onResponse(call: Call<ListMoveResponse>, response: Response<ListMoveResponse>) {
      var moves = response.body()!!.moves
      var total = response.body()!!.total

      if (!isSearching) {
        if (!isFirstFetch) {
          moves.addAll(0, store.state.moveState.moves)
        }

        dispatch(
          MoveAction.UPDATE_STATE(
            ListMoveResponse(
              total, moves
            )
          )
        )
      } else {
        moves.addAll(0, store.state.moveState.searchMoves)
        dispatch(
          MoveAction.UPDATE_SEARCH_MOVES_STATE(
            ListMoveResponse(total, moves)
          )
        )
      }

      if (isFirstFetch) {
        dispatch(
          AppAction.UPDATE_FIRST_FETCH(
            FirstFetchData(isMoveFirstFetch = true)
          )
        )
      }

      dispatch(MoveAction.UPDATE_MOVES_LOADING_STATE(false))
    }
  })
}
