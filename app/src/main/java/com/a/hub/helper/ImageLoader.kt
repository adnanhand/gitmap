package com.a.hub.helper

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

object ImageLoader {

    fun loadImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .centerCrop()
            .fitCenter()
            .apply(RequestOptions().transform(RoundedCorners(15)))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

    fun loadImageInCircle(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .centerCrop()
            .fitCenter()
            .apply(RequestOptions().circleCrop())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

}