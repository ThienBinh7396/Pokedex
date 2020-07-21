package com.example.pokedexapplication

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pokedexapplication.Store.State.RootState
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_splash.*
import org.rekotlin.StoreSubscriber

class SplashActivity : AppCompatActivity(), StoreSubscriber<RootState> {
  private lateinit var backgroundDrawable: GradientDrawable

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)

    initView()
  }

  private fun initView() {

    Handler(Looper.getMainLooper()).postDelayed({
      Intent(this, MainActivity::class.java).apply {
        startActivity(this)
        finish()
      }
    }, 8000)
  }

  override fun newState(state: RootState) {

  }
}