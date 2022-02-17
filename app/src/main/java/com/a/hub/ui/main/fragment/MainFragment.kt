package com.a.hub.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a.hub.R
import com.a.hub.core.livedata.Status
import com.a.hub.data.Constants
import com.a.hub.data.model.Repo
import com.a.hub.databinding.FragmentMainBinding
import com.a.hub.helper.Inform
import com.a.hub.helper.gone
import com.a.hub.helper.startDialogFragment
import com.a.hub.helper.visible
import com.a.hub.ui.adapter.RepositoryAdapter
import com.a.hub.ui.adapter.base.SimpleItemTouchHelperCallback
import com.a.hub.ui.base.fragment.BaseFragment
import com.a.hub.ui.main.MainViewModel
import com.a.hub.ui.main.StateChanged
import com.a.hub.ui.repo.RepoActivity
import com.a.hub.ui.user.UserActivity
import com.a.hub.ui.user.fragment.UserImageFragment

class MainFragment: BaseFragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by activityViewModels()
    private val repositoryAdapter = RepositoryAdapter()
    private lateinit var binding: FragmentMainBinding

    private var loading = true
    private val loadingThreshold = 19

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMainBinding.bind(view)

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
        repositoryAdapter.onItemLongClick = { item: Any, v: View ->
            if(v.id == R.id.item_image){
                if(item is Repo && item.owner != null){
                    val fragment = UserImageFragment()
                    val bundle = Bundle()
                    bundle.putString(Constants.URL, item.owner.avatarUrl)
                    fragment.arguments = bundle
                    startDialogFragment(childFragmentManager, fragment)
                }
            }
        }
        val linearLayoutManager = LinearLayoutManager(context)

        val itemTouchHelper = ItemTouchHelper(SimpleItemTouchHelperCallback(repositoryAdapter))
        itemTouchHelper.attachToRecyclerView(binding.content.list)

        binding.content.list.apply {
            layoutManager = linearLayoutManager
            adapter = repositoryAdapter
            itemAnimator = DefaultItemAnimator()
        }
        binding.content.list.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    if (!loading
                        && linearLayoutManager.itemCount
                        <= (linearLayoutManager.findLastVisibleItemPosition() + loadingThreshold)) {
                        loading = true
                        viewModel.onStateChanged(StateChanged.PageChanged(1))
                        Inform.show(requireContext(), "Loading new page") //TODO remove
                    }
                }
            }
        })

        binding.introHolder.setOnClickListener { viewModel.switchSearch(true) }

        subscribe()
    }

    private fun subscribe() {
        viewModel.repositories.observe(requireActivity(), {
            when(it.status) {
                Status.LOADING -> {
                    repositoryAdapter.swapData(it.data)
                    if(it.data != null && it.data.isEmpty()){
                        binding.content.progress.visible()
                        binding.content.list.gone()
                    }
                }
                Status.SUCCESS -> {
                    loading = false
                    repositoryAdapter.appendData(it.data)
                    binding.content.progress.gone()
                    binding.content.list.visible()
                }
                Status.ERROR -> {}
            }
        })

        viewModel.searchMode.observe(requireActivity(), {
            if(it) {
                binding.introHolder.gone()
                binding.searchHolder.visible()
            }else{
                binding.introHolder.visible()
                binding.searchHolder.gone()
            }
        })
    }
}