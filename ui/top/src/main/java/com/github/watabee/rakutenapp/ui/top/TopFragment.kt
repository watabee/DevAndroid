package com.github.watabee.rakutenapp.ui.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.watabee.rakutenapp.util.ViewModelFactory
import javax.inject.Inject

internal class TopFragment @Inject constructor(
    private val factory: ViewModelFactory.Factory
) : Fragment() {

    private val viewModel: TopViewModel by viewModels { factory.create(this, null) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.log("MainFragment: onCreate()")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top, container, false)
    }
}
