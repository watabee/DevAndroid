package com.github.watabee.rakutenapp.extensions

import android.widget.ImageView
import com.github.watabee.rakutenapp.ui.image.GlideApp

fun ImageView.loadImage(imageUrl: String?) {
    GlideApp.with(this).load(imageUrl).fitCenter().into(this) // TODO: error image
}
