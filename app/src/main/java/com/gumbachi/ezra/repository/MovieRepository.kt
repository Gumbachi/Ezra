package com.gumbachi.ezra.repository

import androidx.compose.runtime.mutableStateListOf
import com.gumbachi.ezra.TMDB_KEY
import com.gumbachi.ezra.data.remote.TmdbApi
import com.gumbachi.ezra.data.remote.responses.movie.MovieCredits
import com.gumbachi.ezra.data.remote.responses.movie.MovieDetails
import com.gumbachi.ezra.data.remote.responses.movie.MovieSearchResult
import com.gumbachi.ezra.model.Movie
import com.gumbachi.ezra.model.WatchStatus

object MovieRepository {

    private val movies = mutableStateListOf<Movie>()
    private var searchResults = mutableStateListOf<MovieSearchResult>()

    private val detailsCache = mutableMapOf<Int, MovieDetails>()
    private val creditsCache = mutableMapOf<Int, MovieCredits>()

    fun remove(id: Int) {
        println("REMOVING ID $id")
        movies.removeIf { id == it.id }
    }

    fun update(updatedMedia: Movie) {
        val index = movies.indexOfFirst { it.id == updatedMedia.id }

        println("Replacing at $index")

        // This is done to force a recomposition -1 indicated movie is not found
        if (index != -1) {
            movies.removeAt(index)
            movies.add(index, updatedMedia)
        }  else {
            movies.add(updatedMedia)
        }

    }

    fun getMoviesByStatus(status: WatchStatus): List<Movie> {
        return movies.filter { status == it.status }.sortedByDescending { it.score }
    }

    fun getSearchResults(): List<MovieSearchResult> {
        return searchResults.toList()
    }

    fun clearSearchResults() {
        searchResults.clear()
    }

    suspend fun searchMovies(query: String) {
        val api = TmdbApi.getInstance()
        println("SEARCHING FOR $query")
        try {
            val movieSearchResults = api.searchMovies(
                apiKey = TMDB_KEY,
                query = query
            )
            searchResults.clear()
            searchResults.addAll(movieSearchResults.results)
        }
        catch (e: Exception) {
            println(e.message)
        }
    }

    suspend fun getMovieDetails(id: Int): MovieDetails? {

        println("GETTING DETAILS FOR $id")

        if (detailsCache.containsKey(id)) {
            return detailsCache[id]
        }

        val api = TmdbApi.getInstance()
        try {
            val movieDetails = api.getMovieDetails(
                apiKey = TMDB_KEY,
                id = id
            )
            detailsCache[id] = movieDetails
            return movieDetails

        } catch (e: Exception) {
            println(e.message)
        }
        return null
    }

    suspend fun getMovieCredits(id: Int): MovieCredits? {

        println("GETTING CREDITS FOR $id")

        if (creditsCache.containsKey(id)) {
            return creditsCache[id]
        }

        val api = TmdbApi.getInstance()
        try {
            val credits = api.getMovieCredits(
                apiKey = TMDB_KEY,
                id = id
            )
            creditsCache[id] = credits
            return credits

        } catch (e: Exception) {
            println(e.message)
        }
        return null
    }

}