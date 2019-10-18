package com.github.watabee.rakutenapp.util

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

interface AppViewModelFactory<T : ViewModel> {
    fun create(handle: SavedStateHandle): T
}