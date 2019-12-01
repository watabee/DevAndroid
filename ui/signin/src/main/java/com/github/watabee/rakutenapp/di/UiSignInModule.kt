package com.github.watabee.rakutenapp.di

import androidx.fragment.app.Fragment
import com.github.watabee.rakutenapp.ui.signin.SignInFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class UiSignInModule {

    @Binds
    @IntoMap
    @FragmentKey(SignInFragment::class)
    abstract fun bindSignInFragment(instance: SignInFragment): Fragment
}
