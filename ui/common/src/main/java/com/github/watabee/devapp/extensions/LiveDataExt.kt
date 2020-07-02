package com.github.watabee.devapp.extensions

import androidx.lifecycle.LiveData
import com.hadilq.liveevent.LiveEvent

fun <T> LiveData<T>.toSingleEvent(): LiveData<T> {
    val event = LiveEvent<T>()
    event.addSource(this, event::setValue)
    return event
}
