package com.github.watabee.devtoapp.extensions

import android.content.res.Resources.Theme
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt

@ColorInt
fun Theme.getColor(@AttrRes attrResId: Int): Int {
    val typedValue = TypedValue()
    resolveAttribute(attrResId, typedValue, true)
    return typedValue.data
}
