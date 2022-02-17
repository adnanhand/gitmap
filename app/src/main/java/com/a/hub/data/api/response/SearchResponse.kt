package com.a.hub.data.api.response

data class SearchResponse<T>(
    val incomplete_results: Boolean,
    val items: List<T>,
    val total_count: Int,
)