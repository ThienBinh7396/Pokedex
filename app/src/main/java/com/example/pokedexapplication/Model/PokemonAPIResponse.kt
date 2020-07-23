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
  var pokemonTypes: MutableList<String>,
  var isSelected: Boolean = false
) : Serializable

data class ListPokemonResponse(
  @SerializedName("pokemons")
  var pokemons: MutableList<Pokemon>,
  @SerializedName("total")
  var total: Int
) : Serializable

data class PokemonEvolution(
  @SerializedName("evolutionLevel")
  var evolutionLevel: Int = 0,
  @SerializedName("evolutionPokemonName")
  var evolutionPokemonName: String,
  @SerializedName("evolutionpokemonImage")
  var evolutionpokemonImage: String,
  @SerializedName("pokemonImage")
  var pokemonImage: String,
  @SerializedName("pokemonName")
  var pokemonName: String
): Serializable

data class PokemonBasicStat(
  @SerializedName("maxValue")
  var maxValue: Int = 0,
  @SerializedName("name")
  var name: String,
  @SerializedName("value")
  var value: Int = 0
): Serializable

data class PokemonHatchTime(
  @SerializedName("cycles")
  var cycles: Int = 0,
  @SerializedName("steps")
  var steps: Int = 0
): Serializable

data class PokemonBreeding(
  @SerializedName("eggGroup")
  var eggGroup: MutableList<String>,
  @SerializedName("hatchTime")
  var hatchTime: PokemonHatchTime,
  @SerializedName("femaleGender")
  var femaleGender: Float = 0f
): Serializable


data class PokemonStats(
  @SerializedName("basicStatses")
  var basicStatses: MutableList<PokemonBasicStat>,
  @SerializedName("breeding")
  var breeding: PokemonBreeding,
  @SerializedName("weaknesses")
  var weaknesses: MutableList<WeaknessesResponse>
): Serializable

data class PokemonDetail(
  @SerializedName("evolutions")
  var evolutions: MutableList<PokemonEvolution>,
  @SerializedName("moves")
  var moves: MutableList<Move>,
  @SerializedName("pokemon")
  var pokemon: Pokemon,
  @SerializedName("stats")
  var stats: PokemonStats
): Serializable

data class WeaknessesResponse(
  @SerializedName("pokemonType")
  var pokemonType: String,
  @SerializedName("effect")
  var effect: Float
) : Serializable