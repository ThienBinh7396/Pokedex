package com.example.pokedexapplication.fragment

import android.content.Intent
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
import com.example.pokedexapplication.DetailActivity
import com.example.pokedexapplication.model.Move
import com.example.pokedexapplication.R
import com.example.pokedexapplication.Store.Action.MoveAction
import com.example.pokedexapplication.Store.State.MoveState
import com.example.pokedexapplication.adapter.RecyclerView.MoveItemAdapter
import com.example.pokedexapplication.databinding.FragmentMoveBinding
import com.example.pokedexapplication.databinding.MoveItemBinding
import com.example.pokedexapplication.store
import com.example.pokedexapplication.viewModel.MoveFragmentViewModel
import org.rekotlin.StoreSubscriber


class MoveFragment : Fragment(), StoreSubscriber<MoveState>,
  MoveItemAdapter.IMoveItemEventListener {
  private lateinit var mMoveFragmentViewModel: MoveFragmentViewModel

  private lateinit var mFragmentMoveBinding: FragmentMoveBinding

  private lateinit var mLinearLayoutManager: LinearLayoutManager

  private lateinit var mMoveItemAdapter: MoveItemAdapter

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    mFragmentMoveBinding =
      DataBindingUtil.inflate<FragmentMoveBinding>(
        inflater,
        R.layout.fragment_move,
        container,
        false
      )

    mMoveFragmentViewModel = MoveFragmentViewModel()

    mFragmentMoveBinding.mMoveFragmentViewModel = mMoveFragmentViewModel

    mLinearLayoutManager = LinearLayoutManager(context)
    mLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL

    mFragmentMoveBinding.rcvMove.layoutManager = mLinearLayoutManager

    mMoveItemAdapter = MoveItemAdapter(this)
    mFragmentMoveBinding.rcvMove.adapter = mMoveItemAdapter

    eventListener()

    return mFragmentMoveBinding.root
  }

  private fun eventListener() {
    mFragmentMoveBinding.rcvMove.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (!recyclerView.canScrollVertically(1)) {

          store.state.appState.let { appState ->
            store.state.moveState.apply {
              val listSize =
                if (appState.isSearching) searchMoves.size else moves.size
              val total = if (appState.isSearching) searchTotal else total

              if (listSize >= total) {
                Toast.makeText(activity, "No move more...", Toast.LENGTH_SHORT).show()
              } else {
                store.dispatch(
                  MoveAction.FETCH_DATA(
                    name = if (appState.isSearching) appState.searchQuery else "",
                    page = listSize / 20 + 1,
                    isSearching = appState.isSearching
                  )
                )
              }
            }
          }
        }
      }
    })
  }

  override fun newState(state: MoveState) {
    state.apply {
      mMoveFragmentViewModel.setMoveList(moves)
      mMoveFragmentViewModel.setSearhMoveList(searchMoves)
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

  override fun onItemClickListener(move: Move) {
    Log.d("Binh", "Move: $move")

    val intent = Intent(activity, DetailActivity::class.java)
    intent.putExtra("data", move)
    intent.putExtra("flag", "Move")

    startActivity(intent)
  }
}