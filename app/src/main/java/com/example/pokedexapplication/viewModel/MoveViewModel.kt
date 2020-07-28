package com.example.pokedexapplication.viewModel

import androidx.databinding.BaseObservable
import com.example.pokedexapplication.BR
import com.example.pokedexapplication.model.Move
import com.example.pokedexapplication.adapter.RecyclerView.MoveItemAdapter

class MoveViewModel(move: Move) : BaseObservable() {
  private var move: Move? = null
  private var mIsLoading: Boolean = true

  private var mMoveItemEventListener: MoveItemAdapter.IMoveItemEventListener? = null

  init {
    this.move = move
  }

  val accuracy
    get() = this.move!!.accuracy

  val accuracyText
    get() = "${this.move!!.accuracy}"

  val effects
    get() = this.move!!.effects

  val name
    get() = this.move!!.name

  val levelText
    get() = if (this.move!!.level != null) "Level ${this.move!!.level}" else ""

  val checkLevelIsNull
    get() = this.move!!.level == null

  val power
    get() = this.move!!.power

  val powerText
    get() = "${this.move!!.power}"

  val pp
    get() = this.move!!.pp

  val ppText
    get() = "${this.move!!.pp}"

  val type
    get() = this.move!!.type

  val isLoading
    get() = this.mIsLoading

  val moveInformation
    get() = this.move

  fun setIsLoading(isLoading: Boolean) {
    this.mIsLoading = isLoading
    notifyChange()
  }

  fun setMoveData(_move: Move) {
    this.move = _move
    notifyChange()
  }

  fun setMoveItemEventListener(moveItemEventListener: MoveItemAdapter.IMoveItemEventListener?) {
    this.mMoveItemEventListener = moveItemEventListener
    notifyPropertyChanged(BR.mMoveFragmentViewModel)
  }

  fun getMoveItemEventListener() = this.mMoveItemEventListener
}
