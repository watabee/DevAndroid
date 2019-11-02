package com.github.watabee.rakutenapp.ui.top

enum class TopPageItem(val className: String) {
    ICHIBA(TopFragment::class.java.name),

    BOOKS(TopFragment::class.java.name),

    FOO(TopFragment::class.java.name),

    BAR(TopFragment::class.java.name);
}
