package com.cats.catsapplication.core.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide


fun View.gone() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun ImageView.loadImage(url: String) {
    Glide.with(this.context)
        .load(url)
        .into(this)
}
