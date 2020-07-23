package com.example.pokedexapplication.common

import android.graphics.Bitmap
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import androidx.renderscript.Allocation
import androidx.renderscript.Element
import androidx.renderscript.RenderScript
import androidx.renderscript.ScriptIntrinsicBlur


class BitmapUtils {
  companion object {
    private fun takeScreenShot(activity: AppCompatActivity): Bitmap? {
      val view = activity.window.decorView
      view.isDrawingCacheEnabled = true
      view.buildDrawingCache()
      val bitmap = view.drawingCache
      val frame = Rect()
      activity.window.decorView.getWindowVisibleDisplayFrame(frame)
      val statusBarHeight: Int = frame.top
      val bitmapResult = Bitmap.createBitmap(
        bitmap,
        0,
        statusBarHeight,
        view.width,
        view.height - statusBarHeight
      )
      view.destroyDrawingCache()
      return bitmapResult
    }

    fun blurActivityView(activity: AppCompatActivity, blur: Float = 24f): Bitmap? {
      val bitmap: Bitmap = takeScreenShot(activity)!!
      val renderScript =
        RenderScript.create(activity)

      // This will blur the bitmapOriginal with a radius of 16 and save it in bitmapOriginal
      val input: Allocation = Allocation.createFromBitmap(
        renderScript,
        bitmap
      ) // Use this constructor for best performance, because it uses USAGE_SHARED mode which reuses memory
      val output: Allocation = Allocation.createTyped(renderScript, input.type)
      val script: ScriptIntrinsicBlur =
        ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
      script.setRadius(blur)
      script.setInput(input)
      script.forEach(output)
      output.copyTo(bitmap)
      return bitmap
    }
  }
}