package com.example.pokedexapplication.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Item(
  @SerializedName("effects")
  var effects: String,
  @SerializedName("image")
  var image: String,
  @SerializedName("name")
  var name: String,
  @SerializedName("price")
  var price: Int
): Serializable

data class ListItemResponse(
  @SerializedName("total")
  var total: Int,
  @SerializedName("items")
  var items: MutableList<Item>
): Serializable
