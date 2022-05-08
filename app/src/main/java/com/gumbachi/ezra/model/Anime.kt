package com.gumbachi.ezra.model

import com.gumbachi.ezra.AnimeDetailsQuery
import com.gumbachi.ezra.SearchAnimeQuery
import kotlin.random.Random

data class Anime(
    override val id: Int,
    override val title: String,
    override val imageURL: String,
    override var score: Double,
    override var status: WatchStatus,
    override var episodesWatched: Int,
    override val totalEpisodes: Int,
    val releaseDate: String,
    override var startDate: String? = null,
    override var finishDate: String? = null,
    override var notes: String = "",
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
                totalEpisodes = searchResult.episodes ?: 1,
                releaseDate = "${details?.season}, ${details?.seasonYear}"
            )
        }

        fun getDummy(): Anime {
            return Anime(
                id = Random.nextInt(),
                title = "Title Goes Here",
                imageURL = "",
                score = Random.nextDouble(10.1),
                status = WatchStatus.values().random(),
                releaseDate = "${Random.nextInt(1, 13)}/${Random.nextInt(1, 29)}/${Random.nextInt(1945, 2030)}",
                episodesWatched = Random.nextInt(500),
                totalEpisodes = Random.nextInt(500, 1000)
            )
        }

        fun getDummies(count: Int): List<Anime> {
            return List(count) { getDummy() }
        }
    }
}
