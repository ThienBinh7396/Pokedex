package com.example.pokedexapplication.Store.State

import com.example.pokedexapplication.model.Pokemon
import com.example.pokedexapplication.model.PokemonDetail
import org.rekotlin.StateType

data class PokemonState(
 var pokemons: MutableList<Pokemon> = mutableListOf(),
 var total: Int = 0,
 var searchPokemons: MutableList<Pokemon> = mutableListOf(),
 var searchTotal: Int = 0,
 var isLoadingPokemons:Boolean = false,
 var isLoadingPokemonDetail: Boolean = false,
 var pokemonDetail: PokemonDetail? = null
): StateType