package com.github.watabee.devtoapp.databinging

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.github.watabee.devtoapp.extensions.loadImage
import com.github.watabee.devtoapp.ui.image.GlideApp

@BindingAdapter("imageUrl", requireAll = false)
fun loadImage(view: ImageView, oldImageUrl: String?, imageUrl: String?) {

    if (oldImageUrl == imageUrl) {
        return
    }

    if (!imageUrl.isNullOrBlank()) {
        view.loadImage(imageUrl)
    } else {
        GlideApp.with(view).clear(view)
        view.setImageDrawable(null)
    }
}
