package com.example.pokedexapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.pokedexapplication.model.Item
import com.example.pokedexapplication.R
import com.example.pokedexapplication.databinding.FragmentItemDetailBinding
import com.example.pokedexapplication.viewModel.ItemViewModel

class ItemDetailFragment : Fragment() {
  private lateinit var mFragmentItemDetailBinding: FragmentItemDetailBinding
  private lateinit var mItemViewModel: ItemViewModel

  private lateinit var item: Item

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    super.onCreateView(inflater, container, savedInstanceState)

    mFragmentItemDetailBinding = DataBindingUtil.inflate(
      inflater,
      R.layout.fragment_item_detail,
      container,
      false
    )

    setupInitData()

    mItemViewModel = ItemViewModel(item)

    mFragmentItemDetailBinding.mItemViewModel = mItemViewModel

    return mFragmentItemDetailBinding.root
  }

  private fun setupInitData() {
    var _item = arguments?.getSerializable("data")

    if (_item == null || _item !is Item) {
      Toast.makeText(context, "Pass data invalid...", Toast.LENGTH_SHORT).show()
      returnMainActivity()
    } else {
      item = _item

    }

  }

  private fun returnMainActivity() {
    activity!!.finish()
  }
}