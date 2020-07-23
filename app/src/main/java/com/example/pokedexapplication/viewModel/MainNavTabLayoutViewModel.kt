package com.example.pokedexapplication.viewModel

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import com.example.pokedexapplication.BR
import com.example.pokedexapplication.GlideApp
import com.example.pokedexapplication.adapter.ViewPager.MainViewPagerAdapter.Companion.MAIN_NAV_TABS

class MainNavTabLayoutViewModel : BaseObservable() {
  private var isSelected: Boolean = false

  private var tabPosition: Int = 0

  fun setIsSelected(isSelected: Boolean) {
    this.isSelected = isSelected
    notifyChange()
  }

  fun getIsSelected() = this.isSelected

  fun setTabPosition(tabPosition: Int) {
    this.tabPosition = tabPosition
    notifyPropertyChanged(BR.mMainNavTabLayoutViewModel)
  }

  fun getTabPosition() = this.tabPosition

  companion object {
    @BindingAdapter("app:bindMainTabLayoutText")
    @JvmStatic
    fun bindMainTabLayoutText(tvLabel: TextView, position: Int) {
      tvLabel.text = MAIN_NAV_TABS[position].title
    }

    @BindingAdapter("app:bindMainTabLayoutIcon")
    @JvmStatic
    fun bindMainTabLayoutIcon(imvIcon: ImageView, viewModel: MainNavTabLayoutViewModel) {
      GlideApp.with(imvIcon)
        .load(
          if (viewModel.isSelected)
            MAIN_NAV_TABS[viewModel.tabPosition].iconActive
          else
            MAIN_NAV_TABS[viewModel.tabPosition].iconDefault
        )
        .into(imvIcon)
    }
  }
}