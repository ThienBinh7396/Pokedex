package com.example.pokedexapplication.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapplication.R
import com.example.pokedexapplication.Store.Action.MoveAction
import com.example.pokedexapplication.Store.Action.PokemonAction
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

    eventListener()

    return mFragmentMoveBinding.root
  }

  private fun eventListener() {
    mFragmentMoveBinding.rcvMove.addOnScrollListener(object: RecyclerView.OnScrollListener(){
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if(!recyclerView.canScrollVertically(1)){

          val listSize = store.state.moveState.moves.size
          val total = store.state.moveState.total

          if(listSize >= total){
            Toast.makeText(activity, "No move more...", Toast.LENGTH_SHORT).show()
          }else{
            store.dispatch(MoveAction.FETCH_DATA(page = listSize / 20 + 1))
          }
        }
      }
    })
  }

  override fun newState(state: MoveState) {
    state.apply {
      mMoveFragmentViewModel.setMoveList(moves)
      mMoveFragmentViewModel.setIsLoading(isLoadingMoves)
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