package com.github.watabee.devtoapp.extensions

import kotlinx.coroutines.Job
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun Job?.isActive(): Boolean {
    contract {
        returns(true) implies (this@isActive != null)
    }

    return this?.isActive ?: false
}
