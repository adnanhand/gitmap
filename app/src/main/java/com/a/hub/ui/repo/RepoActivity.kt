package com.a.hub.ui.repo

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a.hub.R
import com.a.hub.core.SimpleMenuItem
import com.a.hub.core.livedata.Status
import com.a.hub.data.Constants
import com.a.hub.data.model.Repo
import com.a.hub.databinding.ActivityRepoBinding
import com.a.hub.helper.*
import com.a.hub.ui.adapter.MenuAdapter
import com.a.hub.ui.base.BaseActivity
import com.a.hub.ui.repo.fragment.CodeFragment
import com.a.hub.ui.repo.fragment.ContributorsFragment
import com.a.hub.ui.repo.fragment.IssuesFragment
import com.a.hub.ui.repo.fragment.ReadmeFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RepoActivity : BaseActivity() {

    private val viewModel: RepoViewModel by viewModels()
    private lateinit var binding: ActivityRepoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkTheme(
            null,
            binding.root,
            ResourcesCompat.getColor(resources, R.color.primaryVariant, null)
        )

        fullTransparent()

        val url = intent.getStringExtra(Constants.URL)
        if(url != null) viewModel.getRepository(url)

        replaceFragment(binding.fragmentHolder.id, ReadmeFragment(), "ReadmeFragment")

        val menuAdapter = MenuAdapter().apply { swapData(MenuHelper.getRepoMenuItems(resources)) }
        menuAdapter.onItemClick = { item: Any, _: View ->
            if(item is SimpleMenuItem) {
                when (item.id) {
                    1 -> replaceFragment(binding.fragmentHolder.id, ReadmeFragment(), "ReadmeFragment")
                    2 -> replaceFragment(binding.fragmentHolder.id, CodeFragment(), "CodeFragment")
                    3 -> replaceFragment(binding.fragmentHolder.id, IssuesFragment(), "IssuesFragment")
                    4 -> replaceFragment(binding.fragmentHolder.id, CodeFragment(), "CodeFragment")
                    5 -> replaceFragment(binding.fragmentHolder.id, ContributorsFragment(), "ContributorsFragment")
                }
            }
        }
        binding.menuList.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = menuAdapter
        }

        subscribe()

    }

    private fun subscribe() {
        viewModel.message.observe(this, {
            Inform.show(applicationContext, "I: $it")
        })

        viewModel.repo.observe(this, {
            when(it.status){
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    showDetails(it.data)
                }
                Status.ERROR -> {}
            }
        })
    }

    private fun showDetails(it: Repo?) {
        if(it != null){
            if(it.owner != null){
                binding.itemUserName.text = it.owner.login
                ImageLoader.loadImageInCircle(binding.root.context, it.owner.avatarUrl, binding.itemUserImage)
            }
            binding.itemVisibility.text = it.visibility
            binding.itemName.text = it.name

            binding.itemDescription.setTextOrHide(it.description)
            val b = binding.itemLink.setTextOrHide(it.htmlUrl)
            if(b) binding.itemLink.setOnClickListener { v -> startExternal<RepoActivity>(it.htmlUrl) }
            binding.itemLanguage.setTextOrHide(it.language)

            binding.itemUpdated.setTextOrHide(it.updatedAt)

            binding.itemWatchers.text = it.watchersCount.toString()
            binding.itemStars.text = it.stargazersCount.toString()
            binding.itemForks.text = it.forksCount.toString()

            binding.itemGit.setOnClickListener { v -> startExternal<RepoActivity>(it.htmlUrl) }
        }

        binding.expand.itemCollapse.setOnClickListener { v ->
            binding.expand.itemCollapse.setImageResource(
                if(binding.itemDetails.toggle())
                    R.drawable.outline_expand_less_24
                else
                    R.drawable.outline_expand_more_24
            )
        }
    }

}