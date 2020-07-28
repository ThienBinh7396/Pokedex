package com.example.pokedexapplication.common

import android.content.Context
import android.util.Log
import android.view.View
import java.lang.StringBuilder
import java.text.DecimalFormat
import kotlin.math.pow

class NumberUtils {
  companion object {
    fun checkNumberIsInteger(number: Float) = number % 1 == 0f

    fun getGreatestCommonDivisor(numberA: Int, numberB: Int): Int {
      var numA = numberA
      var numB = numberB

      while (numA != numB) {
        if (numA > numB) numA -= numB

        if (numB > numA) numB -= numA
      }

      return numA
    }

    fun minimalistFraction(numerator: Int, denominator: Int): String {
      var greatestCommonDivisor = getGreatestCommonDivisor(numerator, denominator)

      return "${numerator / greatestCommonDivisor}/${denominator / greatestCommonDivisor}"
    }

    private fun getFractionType(number: Float, amountDecimalPart: Int): String {
      var decimalPartPatternString = StringBuilder()

      for (i in 0 until amountDecimalPart) {
        decimalPartPatternString.append("#")
      }

      var decimalFormat = DecimalFormat("#.${decimalPartPatternString}")

      var numberAfterFormat = decimalFormat.format(number).replace(",", ".").toFloat()

      var power = 10.0.pow(amountDecimalPart.toDouble())

      return minimalistFraction((numberAfterFormat * power).toInt(), power.toInt())
    }

    fun formatFloatNumberToFractionType(number: Float?, amountDecimalPart: Int = 2): String {
      if (number == null) return "0"

      return if (checkNumberIsInteger(number)) "${number.toInt()}" else getFractionType(
        number,
        amountDecimalPart
      )
    }

    fun covertDpToPixels(context: Context, dp: Int): Float {
      val scale = context.resources.displayMetrics.scaledDensity
      return dp * scale
    }
    fun covertPixelToDpTo(context: Context, pixel: Float): Float {
      val scale = context.resources.displayMetrics.scaledDensity
      return pixel / scale
    }
  }
}