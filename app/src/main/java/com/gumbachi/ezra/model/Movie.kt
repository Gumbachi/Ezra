package com.gumbachi.ezra.model

import com.gumbachi.ezra.data.remote.responses.movie.MovieDetails
import com.gumbachi.ezra.data.remote.responses.movie.MovieSearchResult
import com.gumbachi.ezra.utils.convertMinutesToRuntime
import com.gumbachi.ezra.utils.getCompleteTMDBPath

data class Movie(
    override val id: Int,
    override val title: String,
    val releaseDate: String,
    val runtime: String,
    override val imageURL: String,
    override var score: Double,
    override var status: WatchStatus,
    override var episodesWatched: Int = 0,
    val budget: Int = 0,
    val revenue: Int = 0,
    val popularity: Double = 0.0,
    val releaseStatus: String = "Released"
) : Media {

    companion object {
        fun fromMovieSearchResult(searchResult: MovieSearchResult, details: MovieDetails?): Movie {
            searchResult.apply {
                return Movie(
                    id = id,
                    title = title,
                    releaseDate = release_date,
                    runtime = convertMinutesToRuntime(details?.runtime),
                    imageURL = getCompleteTMDBPath(poster_path),
                    score = vote_average,
                    status = WatchStatus.PLANNING,
                    budget = details?.budget ?: 0,
                    revenue = details?.revenue ?: 0,
                    popularity = details?.popularity ?: 0.0,
                    releaseStatus = details?.status ?: "Released"
                )
            }
        }
    }
}