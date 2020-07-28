package com.example.pokedexapplication.viewModel

import androidx.databinding.BaseObservable
import com.example.pokedexapplication.model.Item
import com.example.pokedexapplication.adapter.RecyclerView.SingleItemAdapter

class ItemViewModel(item: Item) : BaseObservable() {
  private var mItem: Item? = null

  private var mItemEventListener: SingleItemAdapter.ISingleItemEventListener? = null

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
    get() = this.mItem!!.price.toString()

  val itemEventListener
    get() = this.mItemEventListener

  val item
    get() = this.mItem

  fun setItemEventListener(itemEventListener: SingleItemAdapter.ISingleItemEventListener) {
    this.mItemEventListener = itemEventListener
    notifyChange()
  }

  fun setItemData(item: Item) {
    this.mItem = item
    notifyChange()
  }

}