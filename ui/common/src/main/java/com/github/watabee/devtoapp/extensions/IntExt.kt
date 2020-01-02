package com.github.watabee.devtoapp.extensions

import java.util.Locale

fun Int.toCommaString(): String = String.format(Locale.US, "%,d", this)
