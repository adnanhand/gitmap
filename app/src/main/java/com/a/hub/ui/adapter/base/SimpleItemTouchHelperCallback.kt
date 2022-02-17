package com.a.hub.ui.adapter.base

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.a.hub.R

class SimpleItemTouchHelperCallback(
    private val adapter: ItemTouchHelperAdapter
): ItemTouchHelper.Callback() {

    val ALPHA_FULL = 1.0f

    override fun isLongPressDragEnabled(): Boolean = true
    override fun isItemViewSwipeEnabled(): Boolean = true

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return when (recyclerView.layoutManager) {
            is GridLayoutManager -> {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or
                        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                val swipeFlags = 0
                makeMovementFlags(dragFlags, swipeFlags)
            }
            else -> {
                val dragFlags = 0 //ItemTouchHelper.UP or ItemTouchHelper.DOWN
                val swipeFlags = /*ItemTouchHelper.START or*/ ItemTouchHelper.END
                makeMovementFlags(dragFlags, swipeFlags)
            }
        }
    }

    override fun onMove(
        recyclerView: RecyclerView,
        source: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        if (source.itemViewType != target.itemViewType) return false
        adapter.onItemMove(source.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.onItemDismiss(viewHolder.adapterPosition)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val v: View = viewHolder.itemView.findViewById(R.id.item_foreground)
            if(v != null) {
                val alpha: Float = ALPHA_FULL //- Math.abs(dX) / viewHolder.itemView.width.toFloat()
                v.alpha = alpha
                val max: Float = viewHolder.itemView.width * 0.81f
                v.translationX =
                    if (dX > max) max else dX   //TODO foreground/background
            }
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder is ItemTouchHelperAdapter.ViewHolder) {
                val itemViewHolder: ItemTouchHelperAdapter.ViewHolder = viewHolder
                itemViewHolder.onItemSelected()
            }
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.alpha = ALPHA_FULL
        if (viewHolder is ItemTouchHelperAdapter.ViewHolder) {
            val itemViewHolder: ItemTouchHelperAdapter.ViewHolder = viewHolder
            itemViewHolder.onItemClear()
        }
    }


}