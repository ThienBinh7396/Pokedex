package com.example.pokedexapplication.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedexapplication.R
import com.example.pokedexapplication.Store.State.MoveState
import com.example.pokedexapplication.databinding.FragmentMoveBinding
import com.example.pokedexapplication.databinding.MoveItemBinding
import com.example.pokedexapplication.store
import com.example.pokedexapplication.viewModel.MoveFragmentViewModel
import com.example.pokedexapplication.viewModel.MoveViewModel
import kotlinx.android.synthetic.main.fragment_move.*
import org.rekotlin.StoreSubscriber


class MoveFragment : Fragment(), StoreSubscriber<MoveState> {
  private lateinit var mMoveFragmentViewModel: MoveFragmentViewModel

  private lateinit var mFragmentMoveBinding: FragmentMoveBinding

  private lateinit var mLinearLayoutManager: LinearLayoutManager

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    mFragmentMoveBinding =
      DataBindingUtil.inflate<FragmentMoveBinding>(inflater, R.layout.fragment_move, container, false)

    mMoveFragmentViewModel = MoveFragmentViewModel()

    mFragmentMoveBinding.mMoveFragmentViewModel = mMoveFragmentViewModel

    mLinearLayoutManager = LinearLayoutManager(context)
    mLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL

    mFragmentMoveBinding.rcvMove.layoutManager = mLinearLayoutManager

    return mFragmentMoveBinding.root
  }

  override fun newState(state: MoveState) {
    state.apply {

      Log.d("Binh", "MF: ${moves.size}")
      mMoveFragmentViewModel.setMoveList(moves)
    }
  }

  override fun onStart() {
    super.onStart()

    store.subscribe(this) {
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