package com.github.watabee.devtoapp.ui

import android.view.View
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

@BindingAdapter("goneIfNull")
fun goneIfNull(view: View, value: Any?) {
    view.isGone = value == null
}

@BindingAdapter("colorSchemeColor")
fun setColorSchemeColor(view: SwipeRefreshLayout, @ColorInt color: Int) {
    view.setColorSchemeColors(color)
}
