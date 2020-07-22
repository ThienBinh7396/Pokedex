package com.example.pokedexapplication.Model.API

import com.example.pokedexapplication.Model.ListItemResponse
import com.example.pokedexapplication.Model.ListMoveResponse
import com.example.pokedexapplication.Model.ListPokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IAPIService {
  @GET("items")
  fun fetchItems(
    @Query("name") name: String = "",
    @Query("page") page: Int?,
    @Query("records") records: Int? = null
  ): Call<ListItemResponse>

  @GET("items/{name}")
  fun getItemByName(@Path(value = "name", encoded = true) name: String)

  @GET("moves")
  fun fetchMoves(
    @Query("name") name: String = "",
    @Query("page") page: Int?,
    @Query("records") records: Int? = null
  ): Call<ListMoveResponse>

  @GET("moves/{name}")
  fun getMoveByName(@Path(value = "name", encoded = true) name: String)

  @GET("pokemons")
  fun fetchPokemons(
    @Query("name") name: String = "",
    @Query("page") page: Int?,
    @Query("records") records: Int? = null
  ): Call<ListPokemonResponse>

}