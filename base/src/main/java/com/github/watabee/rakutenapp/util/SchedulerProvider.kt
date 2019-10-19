package com.github.watabee.rakutenapp.util

import io.reactivex.Scheduler

data class SchedulerProvider(
    val main: Scheduler,
    val io: Scheduler,
    val computation: Scheduler
)