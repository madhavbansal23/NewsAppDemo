package com.maddy.newsdemo.adapter

import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.Glide
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("imageUrlCache", "placeholder")
fun loadImageCache(view: AppCompatImageView, imageUrlCache: String?, placeholder: Int) {

    Glide.with(view.context)
        .load(imageUrlCache)
        .apply(
            RequestOptions()
                .placeholder(placeholder)
                .fallback(placeholder)
                .error(placeholder)
                .centerInside()
        )
        .into(view)
}