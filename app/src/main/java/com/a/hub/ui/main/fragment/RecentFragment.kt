package com.a.hub.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.a.hub.R
import com.a.hub.data.model.Recent
import com.a.hub.databinding.FragmentRecentBinding
import com.a.hub.ui.adapter.RecentAdapter
import com.a.hub.ui.base.fragment.BaseDialogFragment
import com.a.hub.ui.main.MainViewModel

class RecentFragment: BaseDialogFragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val recentAdapter = RecentAdapter(R.layout.item_recent_edit)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dim = 0.8f
        initDialog()
        return inflater.inflate(R.layout.fragment_recent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentRecentBinding.bind(view)

        binding.list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recentAdapter
        }

        recentAdapter.onItemClick = { item: Any, v: View ->
            viewModel.deleteRecent(item as Recent)
        }

        subscribe()

    }

    private fun subscribe() {
        viewModel.recent.observe(requireActivity(), {
            recentAdapter.swapData(it)
        })
    }
}