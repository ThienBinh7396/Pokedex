package com.example.pokedexapplication.Model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Pokemon(
  @SerializedName("description")
  var description: String,
  @SerializedName("id")
  var id: String,
  @SerializedName("image")
  var image: String,
  @SerializedName("name")
  var name: String,
  @SerializedName("pokemonTypes")
  var pokemonTypes: MutableList<String>
): Serializable

data class ListPokemonResponse(
  @SerializedName("pokemons")
  var pokemons: MutableList<Pokemon>,
  @SerializedName("total")
  var total: Int
): Serializable

data class WeaknessesResponse(
  @SerializedName("pokemonType")
  var pokemonType: String,
  @SerializedName("effect")
  var effect: Float
): Serializable