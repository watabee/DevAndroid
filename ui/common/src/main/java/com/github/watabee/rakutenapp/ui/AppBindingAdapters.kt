package com.github.watabee.rakutenapp.ui

import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@BindingAdapter("colorSchemeColor")
fun setColorSchemeColor(view: SwipeRefreshLayout, @ColorInt color: Int) {
    view.setColorSchemeColors(color)
}
