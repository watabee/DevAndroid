package com.github.watabee.rakutenapp.pagenation

data class PagedItem<T : Any>(val items: List<T>, val nextPage: Int = NO_PAGE) {
    companion object {
        const val NO_PAGE: Int = -1
    }
}
