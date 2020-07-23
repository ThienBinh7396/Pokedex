package com.example.pokedexapplication.common

import android.os.Build
import android.text.Html
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.pokedexapplication.GlideApp
import com.example.pokedexapplication.R
import com.example.pokedexapplication.common.PokemonTypeUtils.Companion.POKEMON_TYPE

class DataBindingUtils {

  companion object {
    @BindingAdapter("app:bindImageSrc")
    @JvmStatic
    fun bindImageSrc(imv: ImageView, srcImage: Any?) {
      if (srcImage != null) {
        GlideApp.with(imv.context)
          .load(srcImage)
          .placeholder(R.drawable.pokemon_placeholder)
          .fitCenter()
          .into(imv)
      }
    }

    @BindingAdapter("app:bindTypePokemonSrc")
    @JvmStatic
    fun bindTypePokemonSrc(imv: ImageView, type: String) {
      GlideApp.with(imv.context)
        .load(POKEMON_TYPE[type])
        .placeholder(R.drawable.pokemon_placeholder)
        .fitCenter()
        .into(imv)
    }

    @BindingAdapter("app:bindCurrency")
    @JvmStatic
    fun bindCurrency(tv: TextView, num: Int) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        tv.text = Html.fromHtml(
          "<span color='#fafafa'>$num</span> \u20BD",
          Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE
        )
      } else {
        tv.text = Html.fromHtml("<span color='#fafafa'>$num</span> \u20BD")
      }
    }
  }

}