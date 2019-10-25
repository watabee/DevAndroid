package com.github.watabee.rakutenapp.di

import android.os.Looper
import com.github.watabee.rakutenapp.util.AppLogger
import com.github.watabee.rakutenapp.util.Logger
import com.github.watabee.rakutenapp.util.SchedulerProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module(includes = [BaseModule.Provider::class])
abstract class BaseModule {

    @Singleton
    @Binds
    internal abstract fun bindLogger(instance: AppLogger): Logger

    @Module
    internal object Provider {
        @Provides
        @Singleton
        fun provideSchedulerProvider(): SchedulerProvider = SchedulerProvider(
            main = AndroidSchedulers.from(Looper.getMainLooper(), true),
            io = Schedulers.io(),
            computation = Schedulers.computation()
        )
    }
}