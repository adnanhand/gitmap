package com.a.hub.ui.main

data class SortState(
    var order: String = ORDER_DESC,
    var sort: String = SORT_STARS
){
    companion object{
        const val ORDER_ASC = "asc"
        const val ORDER_DESC = "desc"

        const val SORT_UPDATE = "updated"
        const val SORT_STARS = "stars"
        const val SORT_FORKS = "forks"
    }
}

data class SearchState(
    var query: String = "Coroutines",
    var page: Int = 1
)

sealed class StateChanged() {
    data class OrderChanged(val order: String) : StateChanged()
    data class SortChanged(val sort: String) : StateChanged()
    data class QueryChanged(val query: String) : StateChanged()
    data class PageChanged(val page: Int) : StateChanged()
}