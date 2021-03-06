package com.example.pokedexapplication.adapter.viewPager

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.pokedexapplication.fragment.ItemsFragment
import com.example.pokedexapplication.fragment.MoveFragment
import com.example.pokedexapplication.fragment.PokemonFragment
import com.example.pokedexapplication.R

data class MainNavTabData(
  var title: String,
  var iconDefault: Int,
  var iconActive: Int
)

class MainViewPagerAdapter(
  fr: FragmentManager,
  frBehavior: Int = BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) : FragmentPagerAdapter(fr, frBehavior) {
  companion object{
    val MAIN_NAV_TABS = mutableListOf<MainNavTabData>(
      MainNavTabData("Pokemon", R.drawable.ic_pokemon_default, R.drawable.ic_pokemon_active),
      MainNavTabData("Moves", R.drawable.ic_moves_default, R.drawable.ic_moves_active),
      MainNavTabData("Items", R.drawable.ic_item_default, R.drawable.ic_item_active)
    )
  }
  
  override fun getItem(position: Int): Fragment {
    Log.d("Binh", "POSITION: $position")

    return when (position) {
      1 ->
        MoveFragment()
      2 ->
        ItemsFragment()
      else ->
        PokemonFragment()
    }
  }

  override fun getCount() = 3

  override fun getPageTitle(position: Int): CharSequence? = MAIN_NAV_TABS[position].title
}