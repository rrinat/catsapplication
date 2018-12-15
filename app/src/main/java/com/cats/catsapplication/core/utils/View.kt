package com.cats.catsapplication.core.utils

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide


val View.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(context)

fun View.gone() {
    visibility = View.GONE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.isVisible() = visibility == View.VISIBLE

fun ImageView.loadImage(url: String) {
    Glide.with(this.context)
        .load(url)
        .into(this)
}
