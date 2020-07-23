package com.example.pokedexapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pokedexapplication.R
import com.example.pokedexapplication.Store.State.MoveState
import com.example.pokedexapplication.store
import kotlinx.android.synthetic.main.fragment_moves.*
import org.rekotlin.StoreSubscriber


class MovesFragment : Fragment(), StoreSubscriber<MoveState> {
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_moves, container, false)
  }

  override fun newState(state: MoveState) {
    tv.text = state.total.toString()
  }

  override fun onStart() {
    super.onStart()

    store.subscribe(this){
      it.select { it ->
        it.moveState
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()

    store.unsubscribe(this)
  }

}