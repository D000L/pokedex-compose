package com.doool.pokedex.presentation.ui.main.detail

import android.view.View
import com.doool.viewpager.PageModifier
import com.doool.viewpager.ViewPagerTransformer
import kotlin.math.roundToInt

class Transformer(
  private val horizontalOffsetMap: Map<Int, Float>,
  private val verticalOffsetMap: Map<Int, Float>
) : ViewPagerTransformer {

  private val horizontalFirstKey = horizontalOffsetMap.toSortedMap().firstKey()
  private val horizontalLastKey = horizontalOffsetMap.toSortedMap().lastKey()

  private val verticalFirstKey = verticalOffsetMap.toSortedMap().firstKey()
  private val verticalLastKey = verticalOffsetMap.toSortedMap().lastKey()

  private val visibleFirstKey = Math.min(horizontalFirstKey,verticalFirstKey)
  private val visibleLastKey = Math.max(horizontalLastKey,verticalLastKey)

  override fun transformPage(page: PageModifier, position: Float) {
    val left = (position - 0.5).roundToInt()
    val right = (position + 0.5).roundToInt()

    setTranslationX(page, left, right, position)
    setTranslationY(page, left, right, position)

    setVisibility(page, position)
  }

  private fun setTranslationX(page: PageModifier, left: Int, right: Int, position: Float) {
    if (horizontalFirstKey - 0.5 <= position && position <= horizontalLastKey + 0.5) {
      var translationX = -page.left.toFloat()

      val leftPosition = horizontalOffsetMap[left] ?: Float.MIN_VALUE
      val rightPosition = horizontalOffsetMap[right] ?: Float.MAX_VALUE

      translationX += when {
        leftPosition == Float.MIN_VALUE -> rightPosition
        rightPosition == Float.MAX_VALUE -> leftPosition
        else -> (rightPosition - leftPosition) * position
      }
      page.translationX = translationX
    }
  }

  private fun setTranslationY(page: PageModifier, left: Int, right: Int, position: Float) {
    if (verticalFirstKey - 0.5 <= position && position <= verticalLastKey + 0.5) {
      val leftPosition = verticalOffsetMap[left] ?: Float.MIN_VALUE
      val rightPosition = verticalOffsetMap[right] ?: Float.MAX_VALUE

      val translationY = when {
        leftPosition == Float.MIN_VALUE -> rightPosition
        rightPosition == Float.MAX_VALUE -> leftPosition
        else -> (rightPosition - leftPosition) * position
      }
      page.translationY = translationY
    }
  }

  private fun setVisibility(page: PageModifier, position: Float) {
    if (visibleFirstKey <= position && position <= visibleLastKey) {
      page.visibility = View.VISIBLE
    } else {
      page.visibility = View.INVISIBLE
    }
  }
}