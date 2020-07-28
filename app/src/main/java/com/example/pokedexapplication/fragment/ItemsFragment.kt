package com.example.pokedexapplication.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapplication.DetailActivity
import com.example.pokedexapplication.model.Item
import com.example.pokedexapplication.R
import com.example.pokedexapplication.Store.Action.ItemAction
import com.example.pokedexapplication.Store.State.ItemState
import com.example.pokedexapplication.adapter.RecyclerView.SingleItemAdapter
import com.example.pokedexapplication.databinding.FragmentItemsBinding
import com.example.pokedexapplication.store
import com.example.pokedexapplication.viewModel.ItemFragmentViewModel
import org.rekotlin.StoreSubscriber

class ItemsFragment : Fragment(), StoreSubscriber<ItemState>,
  SingleItemAdapter.ISingleItemEventListener {
  private lateinit var mItemFragmentViewModel: ItemFragmentViewModel
  private lateinit var mFragmentItemsBinding: FragmentItemsBinding
  private lateinit var mLinearLayoutManager: LinearLayoutManager

  private lateinit var mSingleItemAdapter: SingleItemAdapter

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

    mSingleItemAdapter = SingleItemAdapter(this)

    mFragmentItemsBinding.rcvMove.adapter = mSingleItemAdapter

    eventListener()

    return mFragmentItemsBinding.root
  }

  private fun eventListener() {
    mFragmentItemsBinding.rcvMove.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (!recyclerView.canScrollVertically(1)) {
          store.state.appState.let { appState ->
            store.state.itemState.apply {
              val listSize =
                if (appState.isSearching) searchItems.size else items.size
              val total = if (appState.isSearching) searchTotal else total

              if (listSize >= total) {
                Toast.makeText(activity, "No item more...", Toast.LENGTH_SHORT).show()
              } else {
                store.dispatch(
                  ItemAction.FETCH_DATA(
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

  override fun newState(state: ItemState) {
    state.apply {
      mItemFragmentViewModel.setItemList(items)
      mItemFragmentViewModel.setIsLoading(isLoadingItems)
      mItemFragmentViewModel.setSearchItemList(searchItems)
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

  override fun onItemClickListener(item: Item) {
    val intent = Intent(activity, DetailActivity::class.java)
    intent.putExtra("data", item)
    intent.putExtra("flag", "Item")

    startActivity(intent)
  }
}