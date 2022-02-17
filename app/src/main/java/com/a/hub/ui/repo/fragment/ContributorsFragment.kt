package com.a.hub.ui.repo.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.a.hub.R
import com.a.hub.core.livedata.Status
import com.a.hub.data.Constants
import com.a.hub.data.model.User
import com.a.hub.databinding.FragmentContributorsBinding
import com.a.hub.helper.gone
import com.a.hub.helper.visible
import com.a.hub.ui.adapter.UserAdapter
import com.a.hub.ui.base.fragment.BaseFragment
import com.a.hub.ui.repo.RepoViewModel
import com.a.hub.ui.user.UserActivity

class ContributorsFragment: BaseFragment(R.layout.fragment_contributors) {

    private val viewModel: RepoViewModel by activityViewModels()
    private val userAdapter: UserAdapter = UserAdapter()

    private lateinit var binding: FragmentContributorsBinding

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentContributorsBinding.bind(view)

        binding.content.list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }

        userAdapter.onItemClick = { item: Any, v: View ->
            startActivity(Intent(activity, UserActivity::class.java).apply {
                putExtra(Constants.URL, (item as User).url)
            })
        }

        subscribe()
    }

    private fun subscribe() {
        viewModel.repo.observe(requireActivity(), {
            when(it.status){
                Status.LOADING -> {
                    binding.content.list.gone()
                    binding.content.progress.visible()
                }
                Status.SUCCESS -> {
                    if(it.data != null){
                        viewModel.getContributors(it.data.contributorsUrl)
                        binding.content.progress.gone()
                        binding.content.list.visible()
                    }
                }
                Status.ERROR -> {}
            }
        })

        viewModel.contributors.observe(requireActivity(), {
            when(it.status){
                Status.LOADING -> {
                    binding.content.list.gone()
                    binding.content.progress.visible()
                }
                Status.SUCCESS -> {
                    if(it.data != null){
                        userAdapter.swapData(it.data)
                        binding.content.progress.gone()
                        binding.content.list.visible()
                    }
                }
                Status.ERROR -> {}
            }

        })
    }
}