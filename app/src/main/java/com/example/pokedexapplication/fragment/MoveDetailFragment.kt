package com.example.pokedexapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.pokedexapplication.model.Move
import com.example.pokedexapplication.R
import com.example.pokedexapplication.databinding.FragmentMoveDetailBinding
import com.example.pokedexapplication.viewModel.MoveViewModel

class MoveDetailFragment : Fragment() {
  private lateinit var mFragmentMoveDetailBinding: FragmentMoveDetailBinding

  private lateinit var mMoveViewModel: MoveViewModel

  private var move: Move? = null

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    mFragmentMoveDetailBinding = DataBindingUtil.inflate(
      inflater,
      R.layout.fragment_move_detail,
      container,
      false
    )

    setupInitData()

    if (move != null) {
      mMoveViewModel = MoveViewModel(move!!)

      mFragmentMoveDetailBinding.mMoveViewModel = mMoveViewModel
    }

    return mFragmentMoveDetailBinding.root
  }

  private fun setupInitData() {
    val _move = arguments?.getSerializable("data")

    if (_move == null || _move !is Move) {
      Toast.makeText(context, "Navigation error...", Toast.LENGTH_SHORT).show()
      returnMainActivity()
    } else {
      this.move = _move as Move
    }
  }

  private fun returnMainActivity() {
    activity!!.finish()
  }
}