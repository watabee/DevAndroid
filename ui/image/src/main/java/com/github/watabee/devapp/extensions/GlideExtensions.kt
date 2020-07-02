package com.github.watabee.devapp.extensions

import android.widget.ImageView
import com.github.watabee.devapp.ui.image.GlideApp

fun ImageView.loadImage(imageUrl: String?) {
    GlideApp.with(this).load(imageUrl).fitCenter().into(this) // TODO: error image
}
