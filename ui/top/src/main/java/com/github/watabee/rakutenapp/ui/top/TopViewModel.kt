package com.github.watabee.rakutenapp.ui.top

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.github.watabee.rakutenapp.util.AppViewModelFactory
import com.github.watabee.rakutenapp.util.Logger
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

internal class TopViewModel @AssistedInject constructor(
    @Assisted private val handle: SavedStateHandle,
    private val logger: Logger
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory : AppViewModelFactory<TopViewModel> {
        override fun create(handle: SavedStateHandle): TopViewModel
    }

    init {
        logger.e("TopViewModel: onCreate()")
        val value: String? = handle["key"]
        if (value.isNullOrBlank()) {
            handle.set("key", "value")
        }
        logger.e("value: $value")
    }

    fun log(message: String) {
        logger.e(message)
    }
}