package com.example.pokedexapplication.adapter.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapplication.R
import com.example.pokedexapplication.common.DataBindingUtils
import com.example.pokedexapplication.databinding.SimpleStringRecycleViewBinding

class SimpleStringAdapter : RecyclerView.Adapter<SimpleStringAdapter.SimpleStringViewHolder>() {
  private var list = mutableListOf<String>()

  class SimpleStringViewHolder(var mSimpleStringRecycleViewBinding: SimpleStringRecycleViewBinding) :
    RecyclerView.ViewHolder(mSimpleStringRecycleViewBinding.root) {

    fun bindData(data: String) {
      mSimpleStringRecycleViewBinding.text = data
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleStringViewHolder =
    SimpleStringViewHolder(
      DataBindingUtil.inflate(
        LayoutInflater.from(parent.context),
        R.layout.simple_string_recycle_view,
        parent,
        false
      )
    )

  override fun getItemCount(): Int = list.size

  override fun onBindViewHolder(holder: SimpleStringViewHolder, position: Int) {
    holder.bindData(list[position])
  }

  fun updateListString(listString: MutableList<String>?) {
    if (listString == null) return

    val diffCallback = SimpleStringCallback(list, listString!!)

    val diffResult = DiffUtil.calculateDiff(diffCallback)
    list.clear()
    list.addAll(listString)

    diffResult.dispatchUpdatesTo(this)
  }

  class SimpleStringCallback(
    var oldList: MutableList<String>,
    var newList: MutableList<String>
  ) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
      oldList[oldItemPosition] == newList[newItemPosition]

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
      oldList[oldItemPosition] == newList[newItemPosition]


  }
}