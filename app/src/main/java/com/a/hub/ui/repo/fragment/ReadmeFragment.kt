package com.a.hub.ui.repo.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import br.tiagohm.markdownview.css.styles.Github
import com.a.hub.R
import com.a.hub.databinding.FragmentReadmeBinding
import com.a.hub.helper.repeatWithViewLifecycle
import com.a.hub.ui.base.fragment.BaseFragment
import com.a.hub.ui.repo.RepoViewModel
import kotlinx.coroutines.launch

class ReadmeFragment: BaseFragment(R.layout.fragment_readme) {

    private val viewModel: RepoViewModel by activityViewModels()
    private lateinit var binding: FragmentReadmeBinding

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentReadmeBinding.bind(view)
        binding.markdownView.addStyleSheet(Github())

        viewModel.markdown.observe(requireActivity(), { showMarkdown(it) })

    }

    //TODO Markdown library isn't responsive and doesn't support dark theme
    //TODO Preload file to text with IO and show on main thread - not supported by library
    private fun showMarkdown(url: String?) {
        if(url != null){
            binding.markdownView.loadMarkdownFromUrl(url)
        }
    }


}