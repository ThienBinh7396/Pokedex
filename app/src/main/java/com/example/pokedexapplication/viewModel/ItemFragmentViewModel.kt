package com.example.pokedexapplication.viewModel

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapplication.BR
import com.example.pokedexapplication.Model.Item
import com.example.pokedexapplication.adapter.RecyclerView.SingleItemAdapter

class ItemFragmentViewModel : BaseObservable() {
  private var mItemList = ObservableArrayList<MutableList<Item>>()

  private var isLoading = false

  init {
    this.mItemList.addAll(0, listOf())
  }


  fun setItemList(itemList: MutableList<Item>) {
    this.mItemList.addAll(0, listOf(itemList))
    notifyPropertyChanged(BR.mItemFragmentViewModel)
  }

  fun getItemList(): ObservableArrayList<MutableList<Item>> {
    return this.mItemList
  }

  fun setIsLoading(isLoading: Boolean) {
    this.isLoading = isLoading
    notifyPropertyChanged(BR.mItemFragmentViewModel)
  }

  fun getIsLoading() = this.isLoading

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