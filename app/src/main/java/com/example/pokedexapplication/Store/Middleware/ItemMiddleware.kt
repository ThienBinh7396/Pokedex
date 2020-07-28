package com.example.pokedexapplication.Store.Middleware

import android.util.Log
import com.example.pokedexapplication.model.API.APIUtils
import com.example.pokedexapplication.model.ListItemResponse
import com.example.pokedexapplication.Store.Action.AppAction
import com.example.pokedexapplication.Store.Action.ItemAction
import com.example.pokedexapplication.Store.State.FirstFetchData
import com.example.pokedexapplication.Store.State.RootState
import com.example.pokedexapplication.store
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
            fetchItemsData(
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

fun fetchItemsData(
  page: Int?,
  name: String,
  isSearching: Boolean,
  isFirstFetch: Boolean,
  dispatch: DispatchFunction
) {
  store.state.itemState.apply {
    if (!isSearching && total != 0 && items.size >= total) {
      return@fetchItemsData
    }

    if (isSearching && searchTotal != 0 && searchItems.size >= searchTotal) {
      return@fetchItemsData
    }
  }

  dispatch(ItemAction.UPDATE_ITEM_LOADING_STATE(true))

  APIUtils.getAPIService().fetchItems(name = name, page = page)
    .enqueue(object : Callback<ListItemResponse> {
      override fun onFailure(call: Call<ListItemResponse>, t: Throwable) {
        dispatch(ItemAction.UPDATE_ITEM_LOADING_STATE(false))
        Log.d("Binh", "Failed: ${t.message}")
      }

      override fun onResponse(call: Call<ListItemResponse>, response: Response<ListItemResponse>) {
        val items = response.body()!!.items
        val total = response.body()!!.total

        if (!isSearching) {
          if (!isFirstFetch) {
            items.addAll(0, store.state.itemState.items)
          }

          dispatch(
            ItemAction.UPDATE_STATE(
              ListItemResponse(total, items)
            )
          )
        }

        if (isSearching) {
          items.addAll(0, store.state.itemState.searchItems)

          dispatch(
            ItemAction.UPDATE_SEARCH_ITEM_STATE(
              ListItemResponse(total, items)
            )
          )
        }

        if (isFirstFetch) {
          dispatch(
            AppAction.UPDATE_FIRST_FETCH(
              FirstFetchData(isItemFirstFetch = true)
            )
          )
        }

        dispatch(ItemAction.UPDATE_ITEM_LOADING_STATE(false))
      }
    })
}
