package com.gumbachi.ezra.data.remote.responses.movie

data class MovieCredits(
    val cast: List<MovieCast>,
    val crew: List<Crew>,
    val id: Int
)