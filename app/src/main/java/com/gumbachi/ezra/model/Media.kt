package com.gumbachi.ezra.model

interface Media {
    val id: Int
    val title: String
    val imageURL: String
    var score: Double
    var status: WatchStatus
    var episodesWatched: Int
    val episodes: Int
        get() = 0

    val isEpisodic: Boolean
        get() = episodes > 0
}