package com.gumbachi.ezra.data.remote.responses.tv

data class TVCredits(
    val cast: List<TVCast>,
    val crew: List<Crew>,
    val id: Int
)