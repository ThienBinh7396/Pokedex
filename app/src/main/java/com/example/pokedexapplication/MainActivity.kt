package com.example.pokedexapplication

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pokedexapplication.Store.Action.AppAction
import com.example.pokedexapplication.adapter.viewPager.MainViewPagerAdapter
import com.example.pokedexapplication.adapter.viewPager.MainViewPagerAdapter.Companion.MAIN_NAV_TABS
import com.example.pokedexapplication.databinding.ActivityMainBinding
import com.example.pokedexapplication.databinding.MainNavTabLayoutBinding
import com.example.pokedexapplication.viewModel.AppStateViewModel
import com.example.pokedexapplication.viewModel.MainNavTabLayoutViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
  TabLayout.OnTabSelectedListener,
  IEditextEventListener,
  TextWatcher {
  private lateinit var mMainViewPagerAdapter: MainViewPagerAdapter

  private lateinit var mMainActivityBinding: ActivityMainBinding

  private lateinit var mAppStateViewModel: AppStateViewModel

  private val tabLayoutViewModels = mutableListOf<MainNavTabLayoutViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setAnimation()

    mMainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    mAppStateViewModel = AppStateViewModel(this)

    setContentView(mMainActivityBinding.root)

    setInitView()
  }

  private fun setInitView() {

    mMainActivityBinding.mainAppBar.mAppStateViewModel = mAppStateViewModel

    mMainViewPagerAdapter = MainViewPagerAdapter(supportFragmentManager)
    vpMain.adapter = mMainViewPagerAdapter

    tlMain.setupWithViewPager(vpMain)
    setupMainTabLayout()

    bindEventListener()
  }

  private fun setAnimation() {
    overridePendingTransition(R.anim.enter_slide_right_anim, R.anim.exit_slide_left_anim)
  }

  private fun setupMainTabLayout() {
    MAIN_NAV_TABS.forEachIndexed { index, _ ->
      val mMainNavTabLayoutBinding =
        DataBindingUtil.inflate<MainNavTabLayoutBinding>(
          LayoutInflater.from(applicationContext),
          R.layout.main_nav_tab_layout,
          null,
          false
        )

      val mMainNavTabLayoutViewModel = MainNavTabLayoutViewModel()

      mMainNavTabLayoutViewModel.setTabPosition(index)

      if (index == 0) {
        mMainNavTabLayoutViewModel.setIsSelected(true)
      }
      mMainNavTabLayoutBinding.mMainNavTabLayoutViewModel = mMainNavTabLayoutViewModel

      mMainActivityBinding.tlMain.getTabAt(index)!!.customView = mMainNavTabLayoutBinding.root

      tabLayoutViewModels.add(mMainNavTabLayoutViewModel)
    }

  }

  private fun bindEventListener() {
    mMainActivityBinding.tlMain.addOnTabSelectedListener(this)

    mMainActivityBinding.mainAppBar.edtSearch.addTextChangedListener(this)
  }


  override fun onTabReselected(tab: TabLayout.Tab?) {
  }

  override fun onTabUnselected(tab: TabLayout.Tab?) {
    if (tab != null) {
      tabLayoutViewModels[tab.position].setIsSelected(false)
    }
  }

  override fun onTabSelected(tab: TabLayout.Tab?) {
    if (tab != null) {
      tabLayoutViewModels[tab.position].setIsSelected(true)
      store.dispatch(AppAction.UPDATE_TITLE_MAIN_ACTIVITY(MAIN_NAV_TABS[tab.position].title))

      resetSearchAction()
    }
  }

  private fun checkSearchQueryIsBlank() = store.state.appState.searchQuery.isBlank()

  private fun resetSearchAction() {
    if (!checkSearchQueryIsBlank()) {
      store.dispatch(AppAction.UPDATE_SEARCH_QUERY(""))
      store.dispatch(AppAction.UPDATE_IS_SEARCHING(false))
    }

    hideEditTextFocus(mMainActivityBinding.mainAppBar.edtSearch)
  }

  override fun onClearClickListener() {
      resetSearchAction()
  }

  override fun onGotoSearchClickListener() {
    if (checkSearchQueryIsBlank()) {
      Toast.makeText(this, "Type something...", Toast.LENGTH_SHORT).show()
    } else {
      store.dispatch(AppAction.UPDATE_IS_SEARCHING(true))
      hideEditTextFocus(mMainActivityBinding.mainAppBar.edtSearch)
    }

  }

  private fun hideEditTextFocus(editText: EditText){
    editText.clearFocus()

    (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(currentFocus?.windowToken, 0)
  }

  override fun afterTextChanged(p0: Editable?) {
  }

  override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
  }

  override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
    store.dispatch(AppAction.UPDATE_SEARCH_QUERY(text.toString()))
  }

}

interface IEditextEventListener {
  fun onClearClickListener()
  fun onGotoSearchClickListener()
}
