package com.example.pokedexapplication.adapter.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapplication.model.Move
import com.example.pokedexapplication.R
import com.example.pokedexapplication.databinding.MoveItemBinding
import com.example.pokedexapplication.viewModel.MoveViewModel

class MoveItemAdapter(var itemEventListener: IMoveItemEventListener?) : RecyclerView.Adapter<MoveItemAdapter.MoveItemViewHolder>() {
  private var mMoveList = mutableListOf<Move>()

  class MoveItemViewHolder(var mMoveItemBinding: MoveItemBinding) :
    RecyclerView.ViewHolder(mMoveItemBinding.moveItem) {
    fun bindData(moveItem: Move, itemEventListener: IMoveItemEventListener?) {
      if (mMoveItemBinding.mMoveViewModel == null) {
        mMoveItemBinding.mMoveViewModel = MoveViewModel(moveItem)
        (mMoveItemBinding.mMoveViewModel as MoveViewModel).setMoveItemEventListener(itemEventListener)
      } else {
        (mMoveItemBinding.mMoveViewModel as MoveViewModel).setMoveData(moveItem)

      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    MoveItemViewHolder(
      DataBindingUtil.inflate<MoveItemBinding>(
        LayoutInflater.from(parent.context), R.layout.move_item, parent, false
      )
    )

  override fun getItemCount() = mMoveList.size

  override fun onBindViewHolder(holder: MoveItemViewHolder, position: Int) {
    holder.bindData(mMoveList[position], itemEventListener)
  }

  fun updateMoveList(_moveList: MutableList<Move>, refresh: Boolean = true) {
    val diffCallback = MoveDiffCallback(mMoveList, _moveList)

    val diffResult = DiffUtil.calculateDiff(diffCallback)

    mMoveList.clear()
    mMoveList.addAll(_moveList)

    diffResult.dispatchUpdatesTo(this)
  }

  class MoveDiffCallback(var oldList: MutableList<Move>, var newList: MutableList<Move>) :
    DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
      oldList[oldItemPosition].name == newList[newItemPosition].name

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
      oldList[oldItemPosition].name == newList[newItemPosition].name
  }

  interface IMoveItemEventListener{
    fun onItemClickListener(move: Move)
  }
}