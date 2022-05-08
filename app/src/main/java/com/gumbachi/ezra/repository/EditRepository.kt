package com.gumbachi.ezra.repository

import com.gumbachi.ezra.SearchAnimeQuery
import com.gumbachi.ezra.data.remote.responses.movie.MovieSearchResult
import com.gumbachi.ezra.data.remote.responses.tv.TVSearchResult
import com.gumbachi.ezra.model.*

object EditRepository {
    lateinit var underEdit: Media

    fun deleteMedia(media: Media) {
        println("Removing Edit Media")
        when (media) {
            is Movie -> MovieRepository.remove(media.id)
            is Show -> TVRepository.remove(media.id)
            is Anime -> AnimeRepository.remove(media.id)
        }
    }

    fun submit(media: Media, newScore: Double, newProgress: Int, newStatus: WatchStatus) {
        println("Updating Edit Media")
        when (media) {
            is Movie -> {
                val updatedMovie = (media).copy(score = newScore, status = newStatus)
                MovieRepository.update(updatedMedia = updatedMovie)
            }
            is Show -> {
                val updatedShow = (media).copy(score = newScore, episodesWatched = newProgress, status = newStatus)
                TVRepository.update(updatedMedia = updatedShow)
            }
            is Anime -> {
                val updatedAnime = (media).copy(score = newScore, episodesWatched = newProgress, status = newStatus)
                AnimeRepository.update(updatedMedia = updatedAnime)
            }
        }
    }

    suspend fun generateEditable(data: Any) {
        when (data) {
            is MovieSearchResult -> {
                val details = MovieRepository.getMovieDetails(data.id)
                underEdit = Movie.fromMovieSearchResult(searchResult = data, details = details)
            }
            is TVSearchResult -> {
                val details = TVRepository.getTVDetails(data.id)
                underEdit = Show.fromTVSearchResult(searchResult = data, details = details)
            }
            is SearchAnimeQuery.Medium -> {
                val details = AnimeRepository.getAnimeDetails(data.id)
                underEdit = Anime.fromAnimeDetails(data, details)
            }
        }
    }
}