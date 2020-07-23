package com.example.pokedexapplication.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.pokedexapplication.GlideApp

class DataBindingUtils {

  companion object {
    @BindingAdapter("app:bindImageSrc")
    @JvmStatic
    fun bindImageSrc(imv: ImageView, srcImage: Any?) {
      if(srcImage != null) {
        GlideApp.with(imv.context)
          .load(srcImage)
          .fitCenter()
          .into(imv)
      }
    }
  }

}