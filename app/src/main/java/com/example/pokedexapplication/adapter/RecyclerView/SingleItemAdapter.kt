package com.example.pokedexapplication.adapter.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapplication.model.Item
import com.example.pokedexapplication.R
import com.example.pokedexapplication.databinding.SingleItemBinding
import com.example.pokedexapplication.viewModel.ItemViewModel

class SingleItemAdapter(var eventListener: ISingleItemEventListener) :
  RecyclerView.Adapter<SingleItemAdapter.SingleItemViewHolder>() {
  private var listItems = mutableListOf<Item>()

  class SingleItemViewHolder(var mSingleItemBinding: SingleItemBinding) : RecyclerView.ViewHolder(
    mSingleItemBinding.root
  ) {
    fun bindItemData(itemData: Item, itemEventListener: ISingleItemEventListener) {
      if (mSingleItemBinding.mItemViewModel == null) {
        mSingleItemBinding.mItemViewModel = ItemViewModel(itemData)
        (mSingleItemBinding.mItemViewModel as ItemViewModel).setItemEventListener(itemEventListener)
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
    holder.bindItemData(listItems[position], eventListener)
  }

  fun updateItemList(_itemList: MutableList<Item>, refresh: Boolean = true) {
    val diffCallback = SingleItemDiffCallback(listItems, _itemList)

    val diffResult = DiffUtil.calculateDiff(diffCallback)

    if (refresh) listItems.clear()
    listItems.addAll(_itemList)

    diffResult.dispatchUpdatesTo(this)
  }

  class SingleItemDiffCallback(
    var oldList: MutableList<Item>,
    var newList: MutableList<Item>
  ) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
      oldList[oldItemPosition].name == newList[newItemPosition].name

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
      oldList[oldItemPosition].name == newList[newItemPosition].name

  }

  interface ISingleItemEventListener {
    fun onItemClickListener(item: Item)
  }

}