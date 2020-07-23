package com.example.pokedexapplication.viewModel

import androidx.databinding.BaseObservable
import com.example.pokedexapplication.Model.Move
import com.google.gson.annotations.SerializedName

class MoveViewModel(move: Move) : BaseObservable() {
  private var move: Move? = null

  init {
    this.move = move
  }

  val accuracy
    get() = this.move!!.accuracy

  val effects
    get() = this.move!!.effects

  val name
    get() = this.move!!.name

  val power
    get() = this.move!!.power

  val pp
    get() = this.move!!.pp

  val type
    get() = this.move!!.type

  fun setMoveData(_move: Move) {
    this.move = _move
    notifyChange()
  }
}
