package com.a.hub.ui.adapter.pager

import android.content.Context
import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

class CustomPagerTransformer(context: Context) : ViewPager.PageTransformer {

    private var scale = 0.16f
    private var maxTranslateOffsetX = 200f
    private lateinit var viewPager: ViewPager

    init {
        maxTranslateOffsetX = dp2px(context, 180)
    }


    override fun transformPage(page: View, position: Float) {
        viewPager = page.parent as ViewPager

        val leftInScreen: Int = page.left - viewPager.scrollX
        val centerXInViewPager: Int = leftInScreen + page.measuredWidth / 2
        val offsetX: Int = centerXInViewPager - viewPager.measuredWidth / 2
        val offsetRate: Float = offsetX.toFloat() * scale / viewPager.measuredWidth
        val scaleFactor = 1f - abs(offsetRate)
        if (scaleFactor > 0) {
            page.scaleX = scaleFactor
            page.scaleY = scaleFactor
            page.translationX = -maxTranslateOffsetX * offsetRate
        }
    }

    private fun dp2px(context: Context, i: Int): Float {
        val m = context.resources.displayMetrics.density
        return (i * m + 0.5f)
    }


}