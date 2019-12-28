package com.github.watabee.devtoapp.ui.top

import com.github.watabee.devtoapp.ui.ichiba.ranking.RankingFragment

enum class TopPageItem(val className: String) {
    ICHIBA(RankingFragment::class.java.name),

    BOOKS(TopFragment::class.java.name),

    FOO(TopFragment::class.java.name),

    BAR(TopFragment::class.java.name);
}
