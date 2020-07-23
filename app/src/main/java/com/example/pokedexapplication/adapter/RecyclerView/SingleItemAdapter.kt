package com.example.pokedexapplication.adapter.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapplication.Model.Item
import com.example.pokedexapplication.R
import com.example.pokedexapplication.databinding.SingleItemBinding
import com.example.pokedexapplication.viewModel.ItemViewModel

class SingleItemAdapter : RecyclerView.Adapter<SingleItemAdapter.SingleItemViewHolder>() {
  private var listItems = mutableListOf<Item>()

  class SingleItemViewHolder(var mSingleItemBinding: SingleItemBinding) : RecyclerView.ViewHolder(
    mSingleItemBinding.root
  ) {
    fun bindItemData(itemData: Item) {
      if (mSingleItemBinding.mItemViewModel == null) {
        mSingleItemBinding.mItemViewModel = ItemViewModel(itemData)
      } else {
        (mSingleItemBinding.mItemViewModel as ItemViewModel).setItemData(itemData)
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    SingleItemViewHolder(
      DataBindingUtil.inflate<SingleItemBinding>(
        LayoutInflater.from(parent.context), R.layout.single_item, parent, false
      )
    )

  override fun getItemCount(): Int = listItems.size

  override fun onBindViewHolder(holder: SingleItemViewHolder, position: Int) {
    holder.bindItemData(listItems[position])
  }

  fun updateItemList(_itemList: MutableList<Item>, refresh: Boolean = true) {
    if (refresh) listItems.clear()
    listItems.addAll(_itemList)
    notifyDataSetChanged()
  }
}