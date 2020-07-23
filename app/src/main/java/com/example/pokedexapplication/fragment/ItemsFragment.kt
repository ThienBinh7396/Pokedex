package com.example.pokedexapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedexapplication.R
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

    return mFragmentItemsBinding.root
  }

  override fun newState(state: ItemState) {
    state.apply {
      mItemFragmentViewModel.setItemList(items)
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