package com.a.hub.ui.adapter.base

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

abstract class BaseViewPagerAdapter<T>(
    private val items: List<T>
): PagerAdapter() {


    override fun getCount(): Int = items.size
    override fun isViewFromObject(view: View, obj: Any): Boolean = view == obj
    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return super.instantiateItem(container, position)
    }

    protected fun getItem(pos: Int): T? {
        return items[pos]
    }
}