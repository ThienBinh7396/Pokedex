package com.example.pokedexapplication.adapter.RecyclerView

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapplication.Model.Move
import com.example.pokedexapplication.R
import com.example.pokedexapplication.databinding.MoveItemBinding
import com.example.pokedexapplication.viewModel.MoveViewModel
import kotlinx.android.synthetic.main.move_item.view.*

class MoveItemAdapter : RecyclerView.Adapter<MoveItemAdapter.MoveItemViewHolder>() {
  private var mMoveList = mutableListOf<Move>()

  class MoveItemViewHolder(var mMoveItemBinding: MoveItemBinding) :
    RecyclerView.ViewHolder(mMoveItemBinding.moveItem) {
    fun bindData(moveItem: Move) {
      if (mMoveItemBinding.mMoveViewModel == null) {
        mMoveItemBinding.mMoveViewModel = MoveViewModel(moveItem)
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
    holder.bindData(mMoveList[position])
  }

  fun updateMoveList(_moveList: MutableList<Move>, refresh: Boolean = true) {
    if (refresh) mMoveList.clear()
    Log.d("Binh", "move list adap: ${_moveList.size}")

    mMoveList.addAll(_moveList)
    notifyDataSetChanged()

  }
}