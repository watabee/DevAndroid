package com.github.watabee.devtoapp.ui

import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.view.isGone
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@BindingAdapter("textOrGoneIfEmpty")
fun textOrGoneIfEmpty(view: TextView, s: CharSequence?) {
    view.text = s
    view.isGone = s.isNullOrEmpty()
}

@BindingAdapter("colorSchemeColor")
fun setColorSchemeColor(view: SwipeRefreshLayout, @ColorInt color: Int) {
    view.setColorSchemeColors(color)
}
