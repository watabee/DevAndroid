package com.github.watabee.rakutenapp.ui.top

import androidx.lifecycle.ViewModel
import com.github.watabee.rakutenapp.util.Logger
import javax.inject.Inject

internal class TopViewModel @Inject constructor(private val logger: Logger) : ViewModel() {

    init {
        logger.e("TopViewModel: onCreate()")
    }

    fun log(message: String) {
        logger.e(message)
    }
}
