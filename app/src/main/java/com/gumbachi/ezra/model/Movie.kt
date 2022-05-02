package com.gumbachi.ezra.model

import com.gumbachi.ezra.data.remote.responses.movie.MovieDetails
import com.gumbachi.ezra.data.remote.responses.movie.MovieSearchResult
import com.gumbachi.ezra.utils.convertMinutesToRuntime
import com.gumbachi.ezra.utils.getCompleteTMDBPath
import kotlin.random.Random

data class Movie(
    override val id: Int,
    override val title: String,
    override val imageURL: String,
    override var score: Double,
    override var status: WatchStatus,
    val releaseDate: String,
    val runtime: String,
    override var episodesWatched: Int = 0,
    override val totalEpisodes: Int = 0,
    override var startDate: String? = null,
    override var finishDate: String? = null,
    override var notes: String = "",
) : Media {

    companion object {
        fun fromMovieSearchResult(searchResult: MovieSearchResult, details: MovieDetails?): Movie {
            searchResult.apply {
                return Movie(
                    id = id,
                    title = title,
                    imageURL = getCompleteTMDBPath(poster_path),
                    score = vote_average,
                    status = WatchStatus.PLANNING,
                    releaseDate = release_date,
                    runtime = convertMinutesToRuntime(details?.runtime),
                )
            }
        }

        fun getDummy(): Movie {
            return Movie(
                id = Random.nextInt(),
                title = "Movie Title",
                imageURL = "",
                score = Random.nextDouble(10.1),
                status = WatchStatus.values().random(),
                releaseDate = "${Random.nextInt(1, 13)}/${Random.nextInt(1, 29)}/${Random.nextInt(1945, 2030)}",
                runtime = "${Random.nextInt(0, 4)}hr ${Random.nextInt(0, 60)}m",
            )
        }

        fun getDummies(count: Int): List<Movie> {
            return List(count) { getDummy() }
        }

    }
}