package com.example.pokedexapplication.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.pokedexapplication.R
import com.example.pokedexapplication.Store.Action.PokemonAction
import com.example.pokedexapplication.Store.State.PokemonState
import com.example.pokedexapplication.adapter.viewPager.PokemonDetailViewPagerAdapter
import com.example.pokedexapplication.common.NumberUtils.Companion.covertDpToPixels
import com.example.pokedexapplication.common.NumberUtils.Companion.covertPixelToDpTo
import com.example.pokedexapplication.databinding.FragmentPokemonDetailBinding
import com.example.pokedexapplication.databinding.FragmentPokemonDetailCollapseBinding
import com.example.pokedexapplication.store
import com.example.pokedexapplication.viewModel.PokemonDetailViewModel
import com.google.android.material.appbar.AppBarLayout
import org.rekotlin.StoreSubscriber
import kotlin.math.abs

class PokemonDetailFragment : Fragment(), StoreSubscriber<PokemonState> {
  private lateinit var mFragmentPokemonDetailBinding: FragmentPokemonDetailCollapseBinding
  private lateinit var mPokemonDetailViewModel: PokemonDetailViewModel

  private lateinit var mPokemonDetailViewPagerAdapter: PokemonDetailViewPagerAdapter

  private lateinit var layoutParams: ViewGroup.MarginLayoutParams

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    mFragmentPokemonDetailBinding = DataBindingUtil.inflate(
      inflater,
      R.layout.fragment_pokemon_detail_collapse,
      container,
      false
    )
    mPokemonDetailViewModel = PokemonDetailViewModel()

    mFragmentPokemonDetailBinding.mPokemonDetailViewModel = mPokemonDetailViewModel

    setupInitView()

    return mFragmentPokemonDetailBinding.root
  }

  private fun setupInitData() {
    val pokemonId = arguments?.getString("data")

    if (pokemonId == null) {
      Toast.makeText(context, "Navigation error...", Toast.LENGTH_SHORT).show()
      returnMainActivity()
    } else {
      store.dispatch(PokemonAction.FETCH_POKEMON_DETAIL(pokemonId))
    }
  }

  private fun setupInitView() {
    mPokemonDetailViewPagerAdapter = PokemonDetailViewPagerAdapter(fragmentManager!!)

    mFragmentPokemonDetailBinding.vpPokemonStats.adapter = mPokemonDetailViewPagerAdapter

    mFragmentPokemonDetailBinding.tlStatInformation
      .setupWithViewPager(mFragmentPokemonDetailBinding.vpPokemonStats)

    mFragmentPokemonDetailBinding.mainAppbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
      updateAppbarLayout(abs(verticalOffset), appBarLayout?.totalScrollRange ?: 0)
    })

    mFragmentPokemonDetailBinding.isCollapsed = false
    layoutParams = mFragmentPokemonDetailBinding.tlStatInformationContainer.layoutParams as ViewGroup.MarginLayoutParams
  }

  private fun updateAppbarLayout(verticalOffset: Int, totalHeight: Int) {
    val diffHeight = totalHeight - verticalOffset

    mFragmentPokemonDetailBinding.contentSlideWhenScroll.translationY =
      if (diffHeight > 100) 0f else (diffHeight - 100f)
    mFragmentPokemonDetailBinding.tvTitleOnCollapsed.alpha =
      if (diffHeight > 100) 0f else (100 - diffHeight) / 100f

    layoutParams.setMargins(
      0,
      if (diffHeight > 100) 0 else (covertDpToPixels(context!!, 14) * (100 - diffHeight) / 100f).toInt(),
      0,
      0
    )

    mFragmentPokemonDetailBinding.tlStatInformationContainer.layoutParams = layoutParams

    mFragmentPokemonDetailBinding.isCollapsed = diffHeight <= 100

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      mFragmentPokemonDetailBinding.mainAppbar.elevation = 0f
      mFragmentPokemonDetailBinding.mainAppbar.outlineProvider = null
    }
  }

  private fun returnMainActivity() {
    activity?.finish()
  }

  override fun newState(state: PokemonState) {
    state.apply {
      mPokemonDetailViewModel.setIsLoading(isLoadingPokemonDetail)
      mPokemonDetailViewModel.setPokemonDetail(pokemonDetail)

      Log.d("Binh", "Detail pokemon $pokemonDetail")
    }
  }

  override fun onStart() {
    super.onStart()

    setupInitData()

    store.subscribe(this) {
      it.select { it ->
        it.pokemonState
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()

    store.unsubscribe(this)
  }

}