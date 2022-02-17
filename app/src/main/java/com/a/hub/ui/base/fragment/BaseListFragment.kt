package com.a.hub.ui.base.fragment

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.a.hub.helper.gone
import com.a.hub.helper.visible

abstract class BaseListFragment: BaseFragment {

    constructor() : super()
    constructor(layoutId: Int) : super(layoutId)

    protected var list: RecyclerView? = null
    protected var progress: View? = null
    protected var error: View? = null
    protected var message: TextView? = null

    protected fun showProgress() {
        list?.gone()
        progress?.visible()
        error?.gone()
    }

    protected fun showContent() {
        list?.visible()
        progress?.gone()
        error?.gone()
    }

    protected fun showError(m: String?) {
        message?.text = m
        list?.gone()
        progress?.gone()
        error?.visible()
    }

    protected abstract fun initContent()
}