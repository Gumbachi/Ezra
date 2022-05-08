package com.gumbachi.ezra.data.remote.responses.movie

data class MovieSearchResults(
    val page: Int,
    val results: List<MovieSearchResult>,
    val total_pages: Int,
    val total_results: Int
)