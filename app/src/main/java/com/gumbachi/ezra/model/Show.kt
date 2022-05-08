package com.gumbachi.ezra.model

import com.gumbachi.ezra.data.remote.responses.tv.TVDetails
import com.gumbachi.ezra.data.remote.responses.tv.TVSearchResult
import kotlin.random.Random

data class Show(
    override val id: Int,
    override val title: String,
    val releaseDate: String,
    override val imageURL: String,
    override var score: Double,
    override var status: WatchStatus,
    override var episodesWatched: Int,
    override val totalEpisodes: Int,
    val inProduction: Boolean = false,
    val numSeasons: Int = 1,
    override var startDate: String? = null,
    override var finishDate: String? = null,
    override var notes: String = "",
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
                    totalEpisodes = details?.number_of_episodes ?: 1,
                    numSeasons = details?.number_of_seasons ?: 1,
                )
            }
        }

        fun getDummy(): Show {
            return Show(
                id = Random.nextInt(),
                title = "Title Goes Here",
                imageURL = "",
                score = Random.nextDouble(10.1),
                status = WatchStatus.values().random(),
                releaseDate = "${Random.nextInt(1, 13)}/${Random.nextInt(1, 29)}/${Random.nextInt(1945, 2030)}",
                episodesWatched = Random.nextInt(0, 500),
                totalEpisodes = Random.nextInt(500, 999),
                numSeasons = Random.nextInt(1, 4)
            )
        }

        fun getDummies(count: Int): List<Show> {
            return List(count) { getDummy() }
        }
    }
}