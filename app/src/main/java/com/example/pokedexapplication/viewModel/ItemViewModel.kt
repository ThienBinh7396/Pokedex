package com.example.pokedexapplication.viewModel

import androidx.databinding.BaseObservable
import com.example.pokedexapplication.Model.Item
import com.google.gson.annotations.SerializedName

class ItemViewModel(item: Item) : BaseObservable() {
  private var mItem: Item? = null

  init {
    this.mItem = item
  }

  val effects
    get() = this.mItem!!.effects

  val image
    get() = this.mItem!!.image

  val name
    get() = this.mItem!!.name

  val price
    get() = this.mItem!!.price


  fun setItemData(item: Item) {
    this.mItem = item

    notifyChange()
  }

}