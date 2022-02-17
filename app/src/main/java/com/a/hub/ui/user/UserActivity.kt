package com.a.hub.ui.user

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a.hub.R
import com.a.hub.core.SimpleMenuItem
import com.a.hub.data.Constants
import com.a.hub.data.model.User
import com.a.hub.databinding.ActivityUserBinding
import com.a.hub.helper.*
import com.a.hub.ui.adapter.MenuAdapter
import com.a.hub.ui.base.BaseActivity
import com.a.hub.ui.user.fragment.UserImageFragment
import com.a.hub.ui.user.fragment.UserInfoFragment
import com.a.hub.ui.user.fragment.UserRepoFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserActivity : BaseActivity() {

    private val viewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityUserBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkTheme(
            null,
            binding.root,
            ResourcesCompat.getColor(resources, R.color.primaryVariant, null)
        )

        fullTransparent()

        val url = intent.getStringExtra(Constants.URL)

        if(url != null) viewModel.getUser(url)

        replaceFragment(binding.fragmentHolder.id, UserInfoFragment(), "UserInfoFragment")

        val menuAdapter = MenuAdapter().apply { swapData(MenuHelper.getUserMenuItems(resources)) }

        menuAdapter.onItemClick = { item: Any, _: View ->
            if(item is SimpleMenuItem) {
                when (item.id) {
                    1 -> replaceFragment(binding.fragmentHolder.id, UserInfoFragment(), "InfoFragment")
                    2 -> replaceFragment(binding.fragmentHolder.id, UserRepoFragment(), "RepositoriesFragment")
                    3 -> Inform.show(applicationContext, "TODO: Organizations")
                }
            }
        }
        binding.menuList.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = menuAdapter
        }

        binding.expand.itemCollapse.setOnClickListener { v ->
            binding.expand.itemCollapse.setImageResource(
                if (binding.itemDetails.toggle())
                    R.drawable.outline_expand_less_24
                else
                    R.drawable.outline_expand_more_24
            )
        }

        subscribe()
    }

    private fun subscribe() {
        viewModel.user.observe(this, {
            updateUserViews(it)
        })
    }

    @SuppressLint("SetTextI18n")
    private fun updateUserViews(it: User?) {
        if(it != null) {
            ImageLoader.loadImageInCircle(binding.root.context, it.avatarUrl, binding.itemImage)
            binding.itemImage.setOnClickListener { v ->
                val fragment = UserImageFragment()
                val bundle = Bundle()
                bundle.putString(Constants.URL, it.avatarUrl)
                fragment.arguments = bundle
                startDialogFragment(supportFragmentManager, fragment)
            }

            binding.itemName.text = it.name
            binding.itemUserName.text = it.login

            val b = binding.itemBlog.setTextOrHide(it.blog)
            if (b) binding.itemBlog.setOnClickListener { v -> startExternal<UserActivity>(it.blog) }

            binding.itemMail.setTextOrHide(it.email)
            binding.itemLocation.setTextOrHide(it.location)
            binding.itemCompany.setTextOrHide(it.company)
            binding.itemBio.setTextOrHide(it.bio)

            binding.itemFollowers.text = "${it.followers} followers"
            binding.itemFollowing.text = "${it.following} following"

            binding.itemGit.setOnClickListener { v -> startExternal<UserActivity>(it.htmlUrl) }
        }
    }

}