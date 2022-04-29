package com.gumbachi.ezra.model

import com.gumbachi.ezra.data.remote.responses.tv.TVDetails
import com.gumbachi.ezra.data.remote.responses.tv.TVSearchResult

data class Show(
    override val id: Int,
    override val title: String,
    val releaseDate: String,
    override val imageURL: String,
    override var score: Double,
    override var status: WatchStatus,
    override var episodesWatched: Int,
    override val episodes: Int,
    val inProduction: Boolean = false,
    val numSeasons: Int = 1,
    val popularity: Double = 0.0
) : Media {
    companion object {
        fun fromTVSearchResult(searchResult: TVSearchResult, details: TVDetails?): Show {
            searchResult.apply {
                return Show(
                    id = id,
                    title = name,
                    releaseDate = first_air_date ,
                    imageURL = "https://image.tmdb.org/t/p/w300$poster_path",
                    score = vote_average,
                    status = WatchStatus.PLANNING,
                    episodesWatched = 0,
                    episodes = details?.number_of_episodes ?: 1,
                    numSeasons = details?.number_of_seasons ?: 1,
                    popularity = details?.popularity ?: 0.0
                )
            }
        }
    }
}