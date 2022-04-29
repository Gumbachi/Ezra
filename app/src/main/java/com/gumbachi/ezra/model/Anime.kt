package com.gumbachi.ezra.model

import com.gumbachi.ezra.AnimeDetailsQuery
import com.gumbachi.ezra.SearchAnimeQuery

data class Anime(
    override val id: Int,
    override val title: String,
    override val imageURL: String,
    override var score: Double,
    override var status: WatchStatus,
    override var episodesWatched: Int,
    override val episodes: Int,
    val releaseDate: String
) : Media {


    companion object {
        fun fromAnimeDetails(searchResult: SearchAnimeQuery.Medium, details: AnimeDetailsQuery.Media?): Anime {
            return Anime(
                id = searchResult.id,
                title = searchResult.title?.english ?: searchResult.title?.romaji ?: searchResult.title?.native ?: "Title Unavailable",
                imageURL = searchResult.coverImage?.large ?: "",
                score = ((searchResult.averageScore ?: 0) / 10).toDouble(),
                status = WatchStatus.PLANNING,
                episodesWatched = 0,
                episodes = searchResult.episodes ?: 1,
                releaseDate = "${details?.season}, ${details?.seasonYear}"
            )
        }
    }
}
