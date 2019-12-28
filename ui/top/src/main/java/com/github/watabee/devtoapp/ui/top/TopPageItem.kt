package com.github.watabee.devtoapp.ui.top

import com.github.watabee.devtoapp.ui.articles.ArticlesFragment

enum class TopPageItem(val className: String) {
    ICHIBA(ArticlesFragment::class.java.name),

    BOOKS(TopFragment::class.java.name),

    FOO(TopFragment::class.java.name),

    BAR(TopFragment::class.java.name);
}
