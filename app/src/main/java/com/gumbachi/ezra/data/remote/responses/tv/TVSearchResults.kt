package com.gumbachi.ezra.data.remote.responses.tv

data class TVSearchResults(
    val page: Int,
    val results: List<TVSearchResult>,
    val total_pages: Int,
    val total_results: Int
)