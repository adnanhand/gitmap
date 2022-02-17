package com.a.hub.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.hub.R
import com.a.hub.data.model.Repo
import com.a.hub.databinding.ItemRepositoryMainBinding
import com.a.hub.helper.ImageLoader
import com.a.hub.ui.adapter.base.BaseAdapter

class RepositoryAdapter: BaseAdapter<RepositoryAdapter.RepositoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val item: Repo = getItem(position) as Repo
        holder.bind(item)
        if(onItemClick != null){
            holder.itemView.setOnClickListener {
                onItemClick?.invoke(item, it)
            }
            holder.binding.itemImage.setOnClickListener {
                onItemClick?.invoke(item, it)
            }
        }
        if(onItemLongClick != null){
            holder.binding.itemImage.setOnLongClickListener {
                onItemLongClick?.invoke(item, it)
                true
            }
        }
    }


    class RepositoryViewHolder(
        val binding: ItemRepositoryMainBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Repo){
            if(item.owner != null) {
                ImageLoader.loadImageInCircle(binding.root.context, item.owner.avatarUrl, binding.itemImage)
            }
            binding.itemFullName.text = item.fullName
            binding.itemName.text = item.name
            binding.itemIssues.text = item.openIssues.toString()
            binding.itemStars.text = item.stargazersCount.toString()
            binding.itemForks.text = item.forksCount.toString()
            binding.itemWatchers.text = item.watchersCount.toString()
            binding.itemDescription.text = if(item.description.isNullOrEmpty()) "No description..." else item.description
            binding.itemForeground.translationX = 0f // TODO move to onSwiped->onItemDismiss
        }

        companion object {
            fun create(parent: ViewGroup): RepositoryViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_repository_main, parent, false)
                return RepositoryViewHolder(ItemRepositoryMainBinding.bind(view))
            }
        }
    }

    override fun onItemDismiss(position: Int) {
        notifyItemChanged(position)
    }
}