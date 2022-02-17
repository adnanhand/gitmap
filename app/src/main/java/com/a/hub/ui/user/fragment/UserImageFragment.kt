package com.a.hub.ui.user.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.a.hub.R
import com.a.hub.data.Constants
import com.a.hub.databinding.FragmentUserImageBinding
import com.a.hub.helper.ImageLoader
import com.a.hub.ui.base.fragment.BaseDialogFragment

class UserImageFragment: BaseDialogFragment() {

    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        url = arguments?.getString(Constants.URL)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initDialog()
        return inflater.inflate(
            R.layout.fragment_user_image,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentUserImageBinding.bind(view)
        ImageLoader.loadImage(view.context, url ?: "", binding.itemImage)
    }
}