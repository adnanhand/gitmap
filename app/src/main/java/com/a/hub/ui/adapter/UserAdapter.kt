package com.a.hub.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.hub.R
import com.a.hub.data.model.User
import com.a.hub.databinding.ItemUserBinding
import com.a.hub.helper.ImageLoader
import com.a.hub.helper.setTextOrHide
import com.a.hub.ui.adapter.base.BaseAdapter

class UserAdapter: BaseAdapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item: User = getItem(position) as User
        holder.bind(item)
        if(onItemClick != null){
            holder.itemView.setOnClickListener {
                onItemClick?.invoke(item, it)
            }
        }
    }


    class UserViewHolder(
        val binding: ItemUserBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(item: User){
            ImageLoader.loadImageInCircle(binding.root.context, item.avatarUrl, binding.itemImage)
            binding.itemFullName.setTextOrHide(item.name)
            binding.itemName.text = item.login
        }

        companion object {
            fun create(parent: ViewGroup): UserViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_user, parent, false)
                return UserViewHolder(ItemUserBinding.bind(view))
            }
        }
    }
}