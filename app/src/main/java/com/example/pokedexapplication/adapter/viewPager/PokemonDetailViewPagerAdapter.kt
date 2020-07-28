package com.example.pokedexapplication.adapter.viewPager

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.pokedexapplication.fragment.PokemonEvolutionFragment
import com.example.pokedexapplication.fragment.PokemonMoveFragment
import com.example.pokedexapplication.fragment.PokemonStatFragment


class PokemonDetailViewPagerAdapter(
  fm: FragmentManager,
  frBehavior: Int = BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) : FragmentPagerAdapter(fm, frBehavior) {
  private var mCurrentPosition = -1

  companion object {
    val POKEMON_DETAIL_TAB_TITLE = listOf(
      "stats",
      "evolutions",
      "moves"
    )
  }

  override fun getItem(position: Int): Fragment {
    return when (position) {
      0 -> PokemonStatFragment()
      1 -> PokemonEvolutionFragment()
      else -> PokemonMoveFragment()
    }
  }

  override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
    super.setPrimaryItem(container, position, `object`)

    if (position != mCurrentPosition) {
      val fragment = `object` as Fragment
      val pager: DynamicHeightViewPager = container as DynamicHeightViewPager
      if (fragment.view != null) {
        mCurrentPosition = position
        pager.measureCurrentView(fragment.view!!)
      }
    }
  }

  override fun getPageTitle(position: Int): CharSequence? =
    POKEMON_DETAIL_TAB_TITLE[position].toUpperCase()

  override fun getCount(): Int = POKEMON_DETAIL_TAB_TITLE.size

}