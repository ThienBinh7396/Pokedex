package com.example.pokedexapplication.viewModel

import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapplication.Model.Item
import com.example.pokedexapplication.adapter.RecyclerView.SingleItemAdapter

class ItemFragmentViewModel : BaseObservable() {
  private var mItemList = ObservableArrayList<MutableList<Item>>()

  init {
    this.mItemList.addAll(0, listOf())
  }


  fun setItemList(itemList: MutableList<Item>) {
    this.mItemList.addAll(0, listOf(itemList))
  }

  fun getItemList() = this.mItemList

  companion object {
    @BindingAdapter("bindItemData")
    @JvmStatic
    fun bindItemData(rcvItem: RecyclerView, itemList: ObservableArrayList<MutableList<Item>>) {
      var adapter =
        if (rcvItem.adapter == null) SingleItemAdapter() else (rcvItem.adapter as SingleItemAdapter)

      if (rcvItem.adapter == null) rcvItem.adapter = adapter

      adapter.updateItemList(itemList[0])
    }
  }
}