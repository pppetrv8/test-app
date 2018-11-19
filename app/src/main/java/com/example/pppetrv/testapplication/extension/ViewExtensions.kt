package com.example.pppetrv.testapplication.extension

import android.view.ViewGroup

fun ViewGroup.enableAllViews(enable: Boolean) {
    for (i in 0 until childCount) {
        val child = getChildAt(i)
        child.isEnabled = enable
        (child as? ViewGroup)?.enableAllViews(enable)
    }
}
