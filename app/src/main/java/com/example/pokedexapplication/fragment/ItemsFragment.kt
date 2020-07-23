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
import com.example.pokedexapplication.Store.Action.ItemAction
import com.example.pokedexapplication.Store.Action.MoveAction
import com.example.pokedexapplication.Store.State.ItemState
import com.example.pokedexapplication.databinding.FragmentItemsBinding
import com.example.pokedexapplication.store
import com.example.pokedexapplication.viewModel.ItemFragmentViewModel
import org.rekotlin.StoreSubscriber

class ItemsFragment : Fragment(), StoreSubscriber<ItemState> {
  private lateinit var mItemFragmentViewModel: ItemFragmentViewModel
  private lateinit var mFragmentItemsBinding: FragmentItemsBinding
  private lateinit var mLinearLayoutManager: LinearLayoutManager

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    mFragmentItemsBinding =
      DataBindingUtil.inflate(inflater, R.layout.fragment_items, container, false)

    mItemFragmentViewModel = ItemFragmentViewModel()

    mFragmentItemsBinding.mItemFragmentViewModel = mItemFragmentViewModel

    mLinearLayoutManager = LinearLayoutManager(context)

    mFragmentItemsBinding.rcvMove.layoutManager = mLinearLayoutManager

    eventListener()

    return mFragmentItemsBinding.root
  }

  private fun eventListener() {
    mFragmentItemsBinding.rcvMove.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (!recyclerView.canScrollVertically(1)) {

          val listSize = store.state.itemState.items.size
          val total = store.state.moveState.total

          Log.d("Binh", "Lít item $listSize , $total")

          if (listSize >= total) {
            Toast.makeText(activity, "No items more...", Toast.LENGTH_SHORT).show()
          } else {
            store.dispatch(ItemAction.FETCH_DATA(page = listSize / 20 + 1))
          }
        }
      }
    })
  }

  override fun newState(state: ItemState) {
    state.apply {
      mItemFragmentViewModel.setItemList(items)
      mItemFragmentViewModel.setIsLoading(isLoadingItems)
    }
  }

  override fun onStart() {
    super.onStart()

    store.subscribe(this) {
      it.select { it ->
        it.itemState
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()

    store.unsubscribe(this)
  }
}