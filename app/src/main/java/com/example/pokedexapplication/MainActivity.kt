package com.example.pokedexapplication

import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pokedexapplication.Adapter.ViewPager.MainViewPagerAdapter
import com.example.pokedexapplication.Store.State.ItemState
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import org.rekotlin.StoreSubscriber

class MainActivity : AppCompatActivity() {
  private lateinit var mMainViewPagerAdapter: MainViewPagerAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setAnimation()
    setContentView(R.layout.activity_main)

    setInitView()
  }

  private fun setInitView() {
    mMainViewPagerAdapter = MainViewPagerAdapter(supportFragmentManager)
    vpMain.adapter = mMainViewPagerAdapter

    tlMain.setupWithViewPager(vpMain)
    setupMainTabLayout()

    addEventListener()
  }

  private fun setAnimation() {
    overridePendingTransition(R.anim.enter_slide_right_anim, R.anim.exit_slide_left_anim)
  }

  private fun setupMainTabLayout() {
    MainViewPagerAdapter.MAIN_NAV_TABS.forEachIndexed { index, mainNavTabData ->
      val view = LayoutInflater.from(this)
        .inflate(R.layout.main_nav_tab_layout, null)

      val imvIcon = view.findViewById<ImageView>(R.id.imvIcon)
      val tvLabel = view.findViewById<TextView>(R.id.tvLabel)

      GlideApp.with(this)
        .load(mainNavTabData.iconDefault)
        .into(imvIcon)

      tvLabel.text = mainNavTabData.title

      tlMain.getTabAt(index)!!.customView = view
    }

    setupTabSelected(tlMain.getTabAt(0)!!)
  }

  private fun addEventListener() {
    tlMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
      override fun onTabReselected(tab: TabLayout.Tab?) {
      }

      override fun onTabUnselected(tab: TabLayout.Tab?) {
        setupTabUnselected(tab!!)
      }

      override fun onTabSelected(tab: TabLayout.Tab?) {
        setupTabSelected(tab!!)
      }
    })
  }

  private fun setupTabUnselected(tab: TabLayout.Tab) {
    val view = tab.customView!!

    val imvIcon = view.findViewById<ImageView>(R.id.imvIcon)
    val tvLabel = view.findViewById<TextView>(R.id.tvLabel)

    GlideApp.with(this)
      .load(MainViewPagerAdapter.MAIN_NAV_TABS[tab.position].iconDefault)
      .into(imvIcon)

    if (Build.VERSION.SDK_INT > 22) {
      tvLabel.setTextColor(resources.getColor(R.color.colorMainNavTabDefault, null))
    } else {
      tvLabel.setTextColor(resources.getColor(R.color.colorMainNavTabDefault))
    }
  }

  private fun setupTabSelected(tab: TabLayout.Tab) {
    val view = tab.customView!!

    val imvIcon = view.findViewById<ImageView>(R.id.imvIcon)
    val tvLabel = view.findViewById<TextView>(R.id.tvLabel)

    GlideApp.with(this)
      .load(MainViewPagerAdapter.MAIN_NAV_TABS[tab.position].iconActive)
      .into(imvIcon)

    if (Build.VERSION.SDK_INT > 22) {
      tvLabel.setTextColor(resources.getColor(R.color.colorMainNavTabActive, null))
    } else {
      tvLabel.setTextColor(resources.getColor(R.color.colorMainNavTabActive))
    }
  }
}