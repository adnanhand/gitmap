package com.a.hub.ui.base.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment: DialogFragment {

    constructor() : super()
    constructor(layoutId: Int) : super(layoutId)

    protected var canceledOnTouchOutside: Boolean = true
    protected val title: String = ""
    protected var dim: Float = 0.98f


    fun initDialog() {
        try {
            dialog?.setCanceledOnTouchOutside(canceledOnTouchOutside)
            dialog?.setTitle(title)
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.setDimAmount(dim)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun cancelDialog() {
        dialog?.dismiss()
    }

}