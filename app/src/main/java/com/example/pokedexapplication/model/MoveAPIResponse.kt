package com.example.pokedexapplication.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Move(
  @SerializedName("accuracy")
  var accuracy: Float,
  @SerializedName("effects")
  var effects: String,
  @SerializedName("name")
  var name: String,
  @SerializedName("power")
  var power: Int = 0,
  @SerializedName("pp")
  var pp: Int = 0,
  @SerializedName("type")
  var type: String = "NORMAL",
  @SerializedName("level")
  var level: Int?
) : Serializable

data class ListMoveResponse(
  @SerializedName("total")
  var total: Int,
  @SerializedName("moves")
  var moves: MutableList<Move>
) : Serializable