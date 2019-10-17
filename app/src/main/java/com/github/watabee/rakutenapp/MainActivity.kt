package com.github.watabee.rakutenapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.watabee.rakutenapp.di.ActivityScope
import com.github.watabee.rakutenapp.di.FragmentKey
import com.github.watabee.rakutenapp.util.Logger
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Inject

class MainActivity : BaseActivity(R.layout.activity_main)

class MainFragment @Inject constructor(private val logger: Logger) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.e("MainFragment: onCreate()")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }
}

@Module(includes = [MainFragmentBuilder::class])
interface MainActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector
    fun contributeMainActivityInjector(): MainActivity
}

@Module
abstract class MainFragmentBuilder {
    @Binds
    @IntoMap
    @FragmentKey(MainFragment::class)
    abstract fun bindFragment(fragment: MainFragment): Fragment
}