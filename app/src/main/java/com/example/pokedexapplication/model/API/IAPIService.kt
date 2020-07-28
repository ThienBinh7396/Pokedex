package com.example.pokedexapplication.model.API

import com.example.pokedexapplication.model.ListItemResponse
import com.example.pokedexapplication.model.ListMoveResponse
import com.example.pokedexapplication.model.ListPokemonResponse
import com.example.pokedexapplication.model.PokemonDetail
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

  @GET("pokemons/{id}")
  fun getDetailPokemonById(
    @Path("id") id: String
  ): Call<PokemonDetail>

}