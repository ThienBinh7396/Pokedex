package com.example.pokedexapplication

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.Slide
import android.view.Gravity
import android.view.Window
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.example.pokedexapplication.Store.Action.ItemAction
import com.example.pokedexapplication.Store.Action.MoveAction
import com.example.pokedexapplication.Store.Action.PokemonAction
import com.example.pokedexapplication.Store.State.AppState
import org.rekotlin.StoreSubscriber


class SplashActivity : AppCompatActivity(), StoreSubscriber<AppState?> {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setAnimation()
    setContentView(R.layout.activity_splash)
    fetchData()
  }

  private fun fetchData() {
    store.dispatch(ItemAction.FETCH_DATA(isFirstFetch = true))
    store.dispatch(MoveAction.FETCH_DATA(isFirstFetch = true))
    store.dispatch(PokemonAction.FETCH_DATA(isFirstFetch = true))
  }

  private fun setAnimation() {
    overridePendingTransition(R.anim.no_anim, R.anim.exit_slide_left_anim)
  }

  override fun onStart() {
    super.onStart()

    store.subscribe(this) {
      it.select { it -> it.appState }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    store.unsubscribe(this)
  }

  override fun newState(state: AppState?) {
    if (state?.isFirstFetchData?.isItemFirstFetch == true && state.isFirstFetchData.isMoveFirstFetch && state.isFirstFetchData.isPokemonFirstFetch) {
      gotoMainActivity()
    }
  }

  private fun gotoMainActivity() {
    Handler(Looper.getMainLooper()).postDelayed({
      Intent(this@SplashActivity, MainActivity::class.java).apply {
        startActivity(this)
        finish()
      }
    }, 500)
  }
}