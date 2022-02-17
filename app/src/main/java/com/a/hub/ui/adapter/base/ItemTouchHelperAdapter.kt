package com.a.hub.ui.adapter.base

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean
    fun onItemDismiss(position: Int)

    interface ViewHolder {
        fun onItemSelected()
        fun onItemClear()
    }
}