package com.a.hub.ui.repo.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.a.hub.R
import com.a.hub.core.livedata.Status
import com.a.hub.databinding.FragmentIssuesBinding
import com.a.hub.ui.adapter.IssueAdapter
import com.a.hub.ui.base.fragment.BaseListFragment
import com.a.hub.ui.repo.RepoViewModel

class IssuesFragment: BaseListFragment(R.layout.fragment_issues) {

    private val viewModel: RepoViewModel by activityViewModels()
    private val issueAdapter: IssueAdapter = IssueAdapter()
    private lateinit var binding: FragmentIssuesBinding

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentIssuesBinding.bind(view)
        initContent()

        binding.content.list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = issueAdapter
        }

        subscribe()
    }

    private fun subscribe(){
        viewModel.repo.observe(requireActivity(), {
            when(it.status){
                Status.LOADING -> {
                    showProgress()
                }
                Status.SUCCESS -> {
                    if(it.data != null) {
                        if (it.data.hasIssues) {
                            issueAdapter.repoName = it.data.fullName
                            viewModel.getIssues(it.data.issuesUrl.replace("{/number}", ""))
                        }
                    }
                    showContent()
                }
                Status.ERROR -> {
                    showError(it.message)
                }
            }

        })

        viewModel.issues.observe(requireActivity(), {
            when(it.status){
                Status.LOADING -> {
                    showProgress()
                }
                Status.SUCCESS -> {
                    if(it.data.isNullOrEmpty()) {
                        showError(getString(R.string.no_data))
                    }else{
                        issueAdapter.swapData(it.data)
                        showContent()
                    }
                }
                Status.ERROR -> {
                    showError(it.message)
                }
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