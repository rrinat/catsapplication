package com.cats.catsapplication.core.utils

import android.app.Activity
import android.graphics.Point
import android.view.Display


fun Activity.getDisplaySize(): Point {
    val display: Display? = windowManager?.defaultDisplay
    val size = Point()
    display?.getSize(size)
    return size
}