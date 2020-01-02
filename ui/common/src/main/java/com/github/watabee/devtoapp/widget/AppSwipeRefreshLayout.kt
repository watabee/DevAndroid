package com.github.watabee.devtoapp.widget

import android.content.Context
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.watabee.devtoapp.extensions.getColor
import com.github.watabee.devtoapp.ui.common.R

class AppSwipeRefreshLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : SwipeRefreshLayout(context, attrs) {

    init {
        setColorSchemeColors(context.theme.getColor(R.attr.colorSecondary))
    }
}
