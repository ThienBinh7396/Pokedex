package com.example.pokedexapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.pokedexapplication.fragment.ItemDetailFragment
import com.example.pokedexapplication.fragment.MoveDetailFragment
import com.example.pokedexapplication.fragment.PokemonDetailFragment
import kotlinx.android.synthetic.main.activity_detail.*
import java.io.Serializable

class DetailActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_detail)

    setupInitFragment()

    imbGoToMainActivity.setOnClickListener { returnMainActivity() }
  }

  private fun setupInitFragment() {
    val flag = intent.getStringExtra("flag")

    Log.d("Binh", "Flag: $flag")

    val data = intent.getSerializableExtra("data")
    when (flag) {
      "Move" ->
        launchFragment(MoveDetailFragment(), data)
      "Item" ->
        launchFragment(ItemDetailFragment(), data)
      "Pokemon" ->
        launchFragment(PokemonDetailFragment(), data)
    }
  }

  private fun launchFragment(fragment: Fragment, data: Serializable?) {
    val bundle = Bundle()
    bundle.putSerializable("data", data)

    fragment.arguments = bundle

    supportFragmentManager.beginTransaction()
      .replace(R.id.fragment_detail_container, fragment)
      .commit()
  }

  private fun returnMainActivity() {
    finish()
  }
}