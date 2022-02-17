package com.a.hub.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.hub.R
import com.a.hub.core.SimpleMenuItem
import com.a.hub.databinding.ItemMenuBinding
import com.a.hub.ui.adapter.base.BaseAdapter

class MenuAdapter: BaseAdapter<MenuAdapter.MenuViewHolder> {

    private var layoutId: Int = R.layout.item_menu

    constructor() : super()
    constructor(layoutId: Int) {
        this.layoutId = layoutId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder.create(parent, layoutId)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val itemSimple: SimpleMenuItem = getItem(position) as SimpleMenuItem
        holder.bind(itemSimple)
        if(onItemClick != null){
            holder.itemView.setOnClickListener {
                onItemClick?.invoke(itemSimple, it)
            }
        }
    }


    class MenuViewHolder(
        val binding: ItemMenuBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(itemSimple: SimpleMenuItem){
            binding.itemImage.setImageResource(itemSimple.icon)
            binding.itemImage.setColorFilter(itemSimple.color)
            binding.itemName.text = itemSimple.name
            binding.itemName.setTextColor(itemSimple.color)
        }

        companion object {
            fun create(parent: ViewGroup, layoutId: Int): MenuViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(layoutId, parent, false)
                return MenuViewHolder(ItemMenuBinding.bind(view))
            }
        }

    }
}