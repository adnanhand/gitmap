package com.a.hub.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.hub.R
import com.a.hub.data.model.Recent
import com.a.hub.databinding.ItemRecentBinding
import com.a.hub.ui.adapter.base.BaseAdapter

class RecentAdapter: BaseAdapter<RecentAdapter.RecentViewHolder> {

    private var layoutId: Int = R.layout.item_recent

    constructor() : super()
    constructor(layoutId: Int) {
        this.layoutId = layoutId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        //return RecentViewHolder(ItemRecentBinding.inflate(LayoutInflater.from(parent.context)))
        return RecentViewHolder.create(parent, layoutId)
    }

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        val item: Recent = getItem(position) as Recent
        holder.bind(item)
        if(onItemClick != null){
            holder.itemView.setOnClickListener {
                onItemClick?.invoke(item, it)
            }
        }
    }


    class RecentViewHolder(
        val binding: ItemRecentBinding
    ): RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun bind(item: Recent){
            binding.itemText.text = item.text
        }

        companion object {
            fun create(parent: ViewGroup, layoutId: Int): RecentViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(layoutId, parent, false)
                return RecentViewHolder(ItemRecentBinding.bind(view))
            }
        }
    }
}