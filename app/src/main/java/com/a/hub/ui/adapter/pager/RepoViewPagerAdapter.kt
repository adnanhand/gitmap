package com.a.hub.ui.adapter.pager

import android.view.LayoutInflater
import android.view.ViewGroup
import com.a.hub.data.model.Repo
import com.a.hub.databinding.ItemRepositoryBinding
import com.a.hub.helper.ImageLoader
import com.a.hub.ui.adapter.base.BaseViewPagerAdapter

class RepoViewPagerAdapter (
    items: List<Repo>
): BaseViewPagerAdapter<Repo>(items) {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val binding = ItemRepositoryBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            false
        )

        val item = getItem(position)

        if(item != null){
            ImageLoader.loadImage(container.context, item.owner.avatarUrl, binding.itemImage)
            binding.itemName.text = item.fullName
        }

        container.addView(binding.root)
        return binding.root
    }
}