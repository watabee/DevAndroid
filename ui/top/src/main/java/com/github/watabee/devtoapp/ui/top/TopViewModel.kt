package com.github.watabee.devtoapp.ui.top

import androidx.lifecycle.ViewModel
import com.github.watabee.devtoapp.util.Logger
import javax.inject.Inject

internal class TopViewModel @Inject constructor(private val logger: Logger) : ViewModel() {

    init {
        logger.e("TopViewModel: onCreate()")
    }

    fun log(message: String) {
        logger.e(message)
    }
}
