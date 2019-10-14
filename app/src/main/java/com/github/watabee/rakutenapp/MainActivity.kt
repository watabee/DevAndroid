package com.github.watabee.rakutenapp

import android.os.Bundle
import com.github.watabee.rakutenapp.di.ActivityScope
import com.github.watabee.rakutenapp.util.Logger
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject lateinit var logger: Logger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logger.e("MainActivity: onCreate()")
    }
}

@Module
interface MainActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector
    fun contributeMainActivityInjector(): MainActivity
}