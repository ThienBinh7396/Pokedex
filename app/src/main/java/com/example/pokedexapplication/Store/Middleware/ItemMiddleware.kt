package com.example.pokedexapplication.Store.Middleware

import android.util.Log
import com.example.pokedexapplication.Model.API.APIUtils
import com.example.pokedexapplication.Model.ListItemResponse
import com.example.pokedexapplication.Store.Action.AppAction
import com.example.pokedexapplication.Store.Action.ItemAction
import com.example.pokedexapplication.Store.State.FirstFetchData
import com.example.pokedexapplication.Store.State.ItemState
import com.example.pokedexapplication.Store.State.RootState
import org.rekotlin.DispatchFunction
import org.rekotlin.Middleware
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal val itemMiddleware: Middleware<RootState> = { dispatch, _ ->
  { next ->
    { action ->
      run {
        when (action) {
          is ItemAction.FETCH_DATA -> {
            fetchItemsData(action.page, action.name, action.isSearching, action.isFirstFetch, dispatch)
          }
        }
        next(action)
      }
    }
  }

}

fun fetchItemsData(page: Int?, name: String, isSearching: Boolean, isFirstFetch: Boolean, dispatch: DispatchFunction){
  APIUtils.getAPIService().fetchItems(name = name, page = page).enqueue(object : Callback<ListItemResponse> {
    override fun onFailure(call: Call<ListItemResponse>, t: Throwable) {
      Log.d("Binh", "Failed: ${t.message}")
    }

    override fun onResponse(call: Call<ListItemResponse>, response: Response<ListItemResponse>) {
      if (!isSearching)
        dispatch(ItemAction.UPDATE_STATE(response.body()))

      if(isFirstFetch)
        dispatch(AppAction.UPDATE_FIRST_FETCH(FirstFetchData(isItemFirstFetch = true)))
    }
  })
}
