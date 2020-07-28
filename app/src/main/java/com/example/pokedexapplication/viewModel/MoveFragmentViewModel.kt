package com.example.pokedexapplication.viewModel

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapplication.model.Move
import com.example.pokedexapplication.adapter.RecyclerView.MoveItemAdapter
import com.example.pokedexapplication.store

class MoveFragmentViewModel : BaseObservable() {
  private var mMoveList: ObservableArrayList<MutableList<Move>> = ObservableArrayList()

  private var mSearchMoveList: ObservableArrayList<MutableList<Move>> = ObservableArrayList()

  private var isLoading = false

  init {
    this.mMoveList.addAll(0, listOf())
    this.mSearchMoveList.addAll(0, listOf())
  }

  fun setMoveList(moveList: MutableList<Move>) {
    this.mMoveList.addAll(0, listOf(moveList))
    notifyChange()
  }

  fun getMoveList() = this.mMoveList

  fun setSearhMoveList(searchMoveList: MutableList<Move>) {
    this.mSearchMoveList.addAll(0, listOf(searchMoveList))
    notifyChange()
  }

  fun getSearchMoveList() = this.mSearchMoveList

  fun getTypeOfMoveIsSearchList() = store.state.appState.isSearching

  fun setIsLoading(isLoading: Boolean) {
    this.isLoading = isLoading
    notifyChange()
  }

  fun getIsLoading() = this.isLoading

  companion object {
    @BindingAdapter("app:bindMoveData")
    @JvmStatic
    fun bindMoveData(rcvMove: RecyclerView, moveList: ObservableArrayList<MutableList<Move>>) {

      if (rcvMove.adapter != null) {
        (rcvMove.adapter as MoveItemAdapter).updateMoveList(moveList[0])
      }
    }

    @BindingAdapter("app:bindMoveDataFromList")
    @JvmStatic
    fun bindMoveDataFromList(rcvMove: RecyclerView, moveList: MutableList<Move>) {
      if (rcvMove.adapter == null) {
        rcvMove.adapter = MoveItemAdapter(null)
        rcvMove.layoutManager = LinearLayoutManager(rcvMove.context)
        (rcvMove.layoutManager as LinearLayoutManager).orientation = LinearLayoutManager.VERTICAL
      }

      Log.d("Binh", "Move list: $moveList")

      if (rcvMove.adapter != null) {
        (rcvMove.adapter as MoveItemAdapter).updateMoveList(moveList)
      }
    }
  }
}