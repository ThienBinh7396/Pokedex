package com.example.pokedexapplication.viewModel

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapplication.Model.Move
import com.example.pokedexapplication.adapter.RecyclerView.MoveItemAdapter

class MoveFragmentViewModel : BaseObservable(){
  private var mMoveList: ObservableArrayList<MutableList<Move>> = ObservableArrayList()

  init {
    this.mMoveList.addAll(0, listOf())
  }

  fun setMoveList(moveList: MutableList<Move>){
    Log.d("Binh", "set move: ${moveList.size}")

    this.mMoveList.addAll(0, listOf(moveList))
    notifyChange()
  }

  fun getMoveList() = this.mMoveList

  companion object{
    @BindingAdapter("app:bindMoveData")
    @JvmStatic
    fun bindMoveData(rcvMove: RecyclerView, moveList: ObservableArrayList<MutableList<Move>>){
      var adapter = if(rcvMove.adapter == null) MoveItemAdapter() else (rcvMove.adapter as MoveItemAdapter)

      if(rcvMove.adapter == null) rcvMove.adapter = adapter

      adapter.updateMoveList(moveList[0])
    }
  }
}