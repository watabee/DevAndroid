package com.github.watabee.devapp.extensions

import java.util.Locale

fun Int.toCommaString(): String = String.format(Locale.US, "%,d", this)
