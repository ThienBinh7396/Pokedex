package com.example.pokedexapplication.Store.Middleware

import android.util.Log
import com.example.pokedexapplication.Model.API.APIUtils
import com.example.pokedexapplication.Model.ListMoveResponse
import com.example.pokedexapplication.Store.Action.AppAction
import com.example.pokedexapplication.Store.Action.MoveAction
import com.example.pokedexapplication.Store.State.FirstFetchData
import com.example.pokedexapplication.Store.State.RootState
import org.rekotlin.Action
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
  APIUtils.getAPIService().fetchMoves(
    page = page,
    name = name
  ).enqueue(object : Callback<ListMoveResponse> {
    override fun onFailure(call: Call<ListMoveResponse>, t: Throwable) {
      Log.d("Binh", "Fetch move data faild!!!")
    }

    override fun onResponse(call: Call<ListMoveResponse>, response: Response<ListMoveResponse>) {
      if (!isSearching)
        dispatch(MoveAction.UPDATE_STATE(response.body()!!))

      if (isFirstFetch)
        dispatch(AppAction.UPDATE_FIRST_FETCH(FirstFetchData(isMoveFirstFetch = true)))
    }
  })
}
