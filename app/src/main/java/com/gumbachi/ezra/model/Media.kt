package com.gumbachi.ezra.model

interface Media {
    val id: Int
    val title: String
    val imageURL: String
    var score: Double
    var status: WatchStatus
    var episodesWatched: Int
    val totalEpisodes: Int
    var startDate: String?
    var finishDate: String?
    var notes: String

    val isEpisodic: Boolean
        get() = totalEpisodes > 0
}