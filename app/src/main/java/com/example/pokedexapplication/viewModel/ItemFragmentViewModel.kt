package com.example.pokedexapplication.viewModel

import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapplication.BR
import com.example.pokedexapplication.model.Item
import com.example.pokedexapplication.adapter.RecyclerView.SingleItemAdapter
import com.example.pokedexapplication.store

class ItemFragmentViewModel : BaseObservable() {
  private var mItemList = ObservableArrayList<MutableList<Item>>()

  private var mSearchItemList = ObservableArrayList<MutableList<Item>>()

  private var isLoading = false

  init {
    this.mItemList.addAll(0, listOf())
    this.mSearchItemList.addAll(0, listOf())
  }


  fun setItemList(itemList: MutableList<Item>) {
    this.mItemList.addAll(0, listOf(itemList))
    notifyPropertyChanged(BR.mItemFragmentViewModel)
  }

  fun getItemList(): ObservableArrayList<MutableList<Item>> {
    return this.mItemList
  }

  fun setSearchItemList(itemList: MutableList<Item>){
    this.mSearchItemList.addAll(0, listOf(itemList))
    notifyPropertyChanged(BR.mItemFragmentViewModel)
  }

  fun getSearchItemList() = this.mSearchItemList

  fun getTypeOfSearchIsSearchList() = store.state.appState.isSearching

  fun setIsLoading(isLoading: Boolean) {
    this.isLoading = isLoading
    notifyPropertyChanged(BR.mItemFragmentViewModel)
  }

  fun getIsLoading() = this.isLoading

  companion object {
    @BindingAdapter("bindItemData")
    @JvmStatic
    fun bindItemData(rcvItem: RecyclerView, itemList: ObservableArrayList<MutableList<Item>>) {
      (rcvItem.adapter as SingleItemAdapter).updateItemList(itemList[0])
    }
  }
}