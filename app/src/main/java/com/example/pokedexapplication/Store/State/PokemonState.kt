package com.example.pokedexapplication.Store.State

import com.example.pokedexapplication.Model.Pokemon
import org.rekotlin.StateType

data class PokemonState(
 var pokemons: MutableList<Pokemon> = mutableListOf(),
 var total: Int = 0
): StateType