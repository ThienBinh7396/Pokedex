package com.example.pokedexapplication.common

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.text.Html
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pokedexapplication.GlideApp
import com.example.pokedexapplication.R
import com.example.pokedexapplication.adapter.RecyclerView.SimpleStringAdapter
import com.example.pokedexapplication.common.PokemonTypeUtils.Companion.POKEMON_TYPE
import com.example.pokedexapplication.common.PokemonTypeUtils.Companion.POKEMON_TYPE_COLOR
import com.example.pokedexapplication.common.PokemonTypeUtils.Companion.POKEMON_TYPE_LG
import com.example.pokedexapplication.model.Pokemon
import java.text.DecimalFormat

class DataBindingUtils {

  companion object {
    @BindingAdapter("app:bindImageSrc")
    @JvmStatic
    fun bindImageSrc(imv: ImageView, srcImage: Any?) {
      if (srcImage != null) {
        GlideApp.with(imv.context)
          .load(srcImage)
          .placeholder(R.drawable.pokemon_placeholder)
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .fitCenter()
          .into(imv)
      }
    }

    @BindingAdapter("app:bindTypePokemonSrc")
    @JvmStatic
    fun bindTypePokemonSrc(imv: ImageView, type: String) {
      imv.setBackgroundResource(R.color.colorTransparent)
      GlideApp.with(imv.context)
        .load(POKEMON_TYPE[type])
        .placeholder(R.drawable.pokemon_placeholder)
        .fitCenter()
        .into(imv)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @BindingAdapter("app:bindClipCenterTypePokemonSrc")
    @JvmStatic
    fun bindClipCenterTypePokemonSrc(imv: ImageView, type: String) {
      var drawable = imv.resources.getDrawable(POKEMON_TYPE[type]!!, null) as BitmapDrawable

      Log.d("Binh", "Bitmap: ${drawable.bitmap.width} ${drawable.bitmap.height}")

      GlideApp.with(imv.context)
        .load(POKEMON_TYPE[type])
        .placeholder(R.drawable.pokemon_placeholder)
        .fitCenter()
        .into(imv)
    }

    @BindingAdapter("app:bindTypePokemonLgSrc")
    @JvmStatic
    fun bindTypePokemonLgSrc(imv: ImageView, type: String) {
      imv.setBackgroundResource(R.color.colorTransparent)
      GlideApp.with(imv.context)
        .load(POKEMON_TYPE_LG[type])
        .placeholder(R.drawable.pokemon_placeholder)
        .fitCenter()
        .into(imv)
    }

    @BindingAdapter("app:bindTypePokemonBackgroundTint")
    @JvmStatic
    fun bindTypePokemonBackgroundTint(view: View, type: String) {
      if (Build.VERSION.SDK_INT >= 23) {
        view.backgroundTintList =
          ColorStateList.valueOf(view.resources.getColor(POKEMON_TYPE_COLOR[type]!!, null))

      } else {
        view.setBackgroundColor(POKEMON_TYPE_COLOR[type]!!)
      }
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

    @BindingAdapter("app:goneUnless")
    @JvmStatic
    fun goneUnless(view: View, isVisibility: Boolean) {
      view.visibility = if (isVisibility) View.VISIBLE else View.GONE
    }

    @BindingAdapter("app:goneUnlessWithInvisible")
    @JvmStatic
    fun goneUnlessWithInvisible(view: View, isVisibility: Boolean) {
      view.visibility = if (isVisibility) View.VISIBLE else View.INVISIBLE
    }

    @BindingAdapter("app:goneUnlessWithSlideAnimation")
    @JvmStatic
    fun goneUnlessWithSlideAnimation(view: View, isVisibility: Boolean) {
      val animation: Animation = AnimationUtils.loadAnimation(
        view.context,
        if (isVisibility) R.anim.enter_slide_bottom_anim else R.anim.exit_slide_bottom_anim
      )

      view.visibility = if (isVisibility) View.VISIBLE else View.GONE

      view.startAnimation(animation)
    }

    @BindingAdapter("app:goneUnlessWithFadeAnimation")
    @JvmStatic
    fun goneUnlessWithFadeAnimation(view: View, isVisibility: Boolean) {
      val animation: Animation = AnimationUtils.loadAnimation(
        view.context,
        if (isVisibility) R.anim.enter_fade_anim else R.anim.exit_fade_anim
      )

      view.visibility = if (isVisibility) View.VISIBLE else View.GONE

      view.startAnimation(animation)
    }

    @BindingAdapter("app:bindStringList")
    @JvmStatic
    fun bindStringList(rcvString: RecyclerView, listString: MutableList<String>?) {

      if (rcvString.adapter == null) {
        rcvString.adapter = SimpleStringAdapter()
        rcvString.layoutManager = LinearLayoutManager(rcvString.context)

        (rcvString.layoutManager as LinearLayoutManager).orientation = LinearLayoutManager.VERTICAL
      }

      if (listString != null) {
        (rcvString.adapter as SimpleStringAdapter).updateListString(listString)
      }
    }

    @BindingAdapter("app:bindStepsText")
    @JvmStatic
    fun bindStepsText(textView: TextView, steps: Int?) {
      textView.text = "$steps Steps"
    }

    @BindingAdapter("app:bindCyclesText")
    @JvmStatic
    fun bindCyclesText(textView: TextView, cycles: Int?) {
      textView.text = "$cycles Cycles"
    }

    @BindingAdapter("app:bindPercentText")
    @JvmStatic
    fun bindPercentText(textView: TextView, percent: Float) {
      val decimalFormat = DecimalFormat("#.#")
      textView.text = "${decimalFormat.format(percent)}%"
    }

    @BindingAdapter("app:bindProgressValue")
    @JvmStatic
    fun bindProgressValue(progressBar: ProgressBar, percent: Float) {
      progressBar.progress = percent.toInt()
    }


    @BindingAdapter("app:bindBackgroundCollapsedView")
    @JvmStatic
    fun bindBackgroundCollapsedView(view: View, isCollapsed: Boolean) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        if (isCollapsed) {
          view.background =
            view.resources.getDrawable(R.drawable.background_content_fragment_detail, null)
        } else {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setBackgroundColor(view.resources.getColor(R.color.colorBackground, null))
          }
        }
      } else {
        if (isCollapsed) {
          view.background =
            view.resources.getDrawable(R.drawable.background_content_fragment_detail)
        } else {
          view.setBackgroundColor(view.resources.getColor(R.color.colorBackground))
        }
      }
    }
  }

}