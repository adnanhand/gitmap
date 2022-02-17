package com.a.hub.ui.adapter.base

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<V: RecyclerView.ViewHolder>: RecyclerView.Adapter<V>(), ItemTouchHelperAdapter {
    var onItemClick: ((item: Any, view: View) -> Unit)? = null
    var onItemLongClick: ((item: Any, view: View) -> Unit)? = null

    var onItemAction: ((action: Int) -> Unit)? = null
    var onStartDragListener: ((viewHolder: RecyclerView.ViewHolder) -> Unit)? = null

    protected var items: MutableList<Any>? = null

    override fun getItemCount(): Int = items?.size ?: 0

    protected fun getItem(position: Int): Any {
        return items?.get(position)!!
    }

    @SuppressLint("NotifyDataSetChanged")
    fun swapData(newItems: List<Any>?) {
        if (newItems != null) {
            items = newItems.toMutableList()
            notifyDataSetChanged() //TODO
        }
    }

    fun appendData(newItems: List<Any>?) {
        if (newItems != null) {
            if(items == null){
                swapData(newItems)
            }else{
                val start = itemCount
                items?.addAll(newItems)
                notifyItemRangeInserted(start+1, newItems.size)
            }
        }
    }


    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        return false
    }

    override fun onItemDismiss(position: Int) {

    }

}