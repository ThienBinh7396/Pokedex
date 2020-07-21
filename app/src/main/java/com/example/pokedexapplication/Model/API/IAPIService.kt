package com.example.pokedexapplication.Model.API

import com.example.pokedexapplication.Model.ListItemResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IAPIService {
  @GET("items")
  fun fetchItems(
    @Query("page") page: Int?
  ): Call<ListItemResponse>

  @GET("items/{name}")
  fun getItemByName(@Path(value = "name", encoded = true) name: String)


}