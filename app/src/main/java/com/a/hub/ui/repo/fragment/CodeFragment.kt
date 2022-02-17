package com.a.hub.ui.repo.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.a.hub.R
import com.a.hub.ui.base.fragment.BaseFragment
import com.a.hub.ui.repo.RepoViewModel

class CodeFragment: BaseFragment(R.layout.fragment_code) {

    private val viewModel: RepoViewModel by activityViewModels()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}