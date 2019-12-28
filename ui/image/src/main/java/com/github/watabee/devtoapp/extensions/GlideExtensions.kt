package com.github.watabee.devtoapp.extensions

import android.widget.ImageView
import com.github.watabee.devtoapp.ui.image.GlideApp

fun ImageView.loadImage(imageUrl: String?) {
    GlideApp.with(this).load(imageUrl).fitCenter().into(this) // TODO: error image
}
