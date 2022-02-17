package com.a.hub.ui.user.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.a.hub.R
import com.a.hub.core.livedata.Status
import com.a.hub.data.Constants
import com.a.hub.data.model.Repo
import com.a.hub.databinding.FragmentUserReposBinding
import com.a.hub.ui.adapter.RepositoryAdapter
import com.a.hub.ui.base.fragment.BaseListFragment
import com.a.hub.ui.repo.RepoActivity
import com.a.hub.ui.user.UserActivity
import com.a.hub.ui.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

class UserRepoFragment: BaseListFragment(R.layout.fragment_user_repos) {

    private val viewModel: UserViewModel by activityViewModels()
    private val repositoryAdapter = RepositoryAdapter()
    private lateinit var binding: FragmentUserReposBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentUserReposBinding.bind(view)
        initContent()

        repositoryAdapter.onItemClick = { item: Any, v: View ->
            if(v.id == R.id.item_image){
                startActivity(Intent(activity, UserActivity::class.java).apply {
                    putExtra(Constants.URL, (item as Repo).owner.url)
                })
            }else{
                startActivity(Intent(activity, RepoActivity::class.java).apply {
                    putExtra(Constants.URL, (item as Repo).url)
                    putExtra(Constants.PATH, item.fullName)
                })
            }
        }

        binding.content.list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = repositoryAdapter
        }

        subscribe()
    }

    private fun subscribe() {
        viewModel.repositories.observe(requireActivity(), {
            when(it.status) {
                Status.LOADING -> {
                    repositoryAdapter.swapData(it.data)
                    showProgress()
                }
                Status.SUCCESS -> {
                    if(it.data.isNullOrEmpty()) {
                        showError(getString(R.string.no_data))
                    }else{
                        repositoryAdapter.appendData(it.data)
                        showContent()
                    }
                }
                Status.ERROR -> { showError(it.message) }
            }
        })
    }

    override fun initContent() {
        list = binding.content.list
        progress = binding.content.progress
        error = binding.content.error
        message = binding.content.message
    }
}