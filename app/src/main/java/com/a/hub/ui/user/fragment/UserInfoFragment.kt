package com.a.hub.ui.user.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.a.hub.R
import com.a.hub.core.livedata.Status
import com.a.hub.data.model.Repo
import com.a.hub.databinding.FragmentUserInfoBinding
import com.a.hub.ui.adapter.pager.CustomPagerTransformer
import com.a.hub.ui.adapter.pager.RepoViewPagerAdapter
import com.a.hub.ui.base.fragment.BaseFragment
import com.a.hub.ui.user.UserViewModel
import kotlin.math.min

class UserInfoFragment: BaseFragment(R.layout.fragment_user_info) {

    private val viewModel: UserViewModel by activityViewModels()

    private lateinit var binding: FragmentUserInfoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentUserInfoBinding.bind(view)
        subscribe()
    }

    private fun subscribe() {
        viewModel.repositories.observe(requireActivity(), {
            when(it.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    if(it.data?.size ?: 0 > 0)
                        initViewPager(
                            it.data?.subList(0, min(3, it.data.size)
                            ) ?: listOf())
                }
                Status.ERROR -> {}
            }
        })
    }

    private fun initViewPager(list: List<Repo>) {
        val pagerAdapter = RepoViewPagerAdapter(list)
        binding.viewpager.apply {
            adapter = pagerAdapter
            /*clipToPadding = false
            setPadding(80, 0, 80, 0)*/
            pageMargin = 40
            setPageTransformer(false, CustomPagerTransformer(context))
        }
    }
}