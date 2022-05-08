package com.gumbachi.ezra.repository

import androidx.compose.runtime.mutableStateListOf
import com.gumbachi.ezra.TMDB_KEY
import com.gumbachi.ezra.data.remote.TmdbApi
import com.gumbachi.ezra.data.remote.responses.tv.TVCredits
import com.gumbachi.ezra.data.remote.responses.tv.TVDetails
import com.gumbachi.ezra.data.remote.responses.tv.TVSearchResult
import com.gumbachi.ezra.model.Show
import com.gumbachi.ezra.model.WatchStatus

object TVRepository {

    private val shows = mutableStateListOf<Show>()
    private val searchResults = mutableStateListOf<TVSearchResult>()
    private val detailsCache = mutableMapOf<Int, TVDetails>()
    private val creditsCache = mutableMapOf<Int, TVCredits>()

    fun remove(id: Int) {
        println("REMOVING ID $id")
        shows.removeIf { id == it.id }
    }

    fun update(updatedMedia: Show) {
        val index = shows.indexOfFirst { it.id == updatedMedia.id }

        println("Replacing at $index")

        // This is done to force a recomposition
        if (index != -1) {
            shows.removeAt(index)
            shows.add(index, updatedMedia)
        } else {
            shows.add(updatedMedia)
        }

    }

    fun getShowsByStatus(status: WatchStatus): List<Show> {
        return shows.filter { status == it.status }.sortedByDescending { it.score }
    }

    fun getSearchResults(): List<TVSearchResult> {
        return searchResults.toList()
    }

    suspend fun searchTVShows(query: String) {
        val api = TmdbApi.getInstance()
        println("TV SHOW SEARCHING FOR $query")
        try {
            val tvSearchResults = api.searchTV(
                apiKey = TMDB_KEY,
                query = query
            )
            val showList = tvSearchResults.results
            searchResults.clear()
            searchResults.addAll(showList)
        }
        catch (e: Exception) {
            println(e)
            println(e.message)
            println(e.cause)
        }
    }

    suspend fun getTVDetails(id: Int): TVDetails? {

        println("GETTING DETAILS FOR $id")

        if (detailsCache.containsKey(id)) {
            return detailsCache[id]
        }

        val api = TmdbApi.getInstance()
        try {
            val tvDetails = api.getTVDetails(
                apiKey = TMDB_KEY,
                id = id
            )
            detailsCache[id] = tvDetails
            return tvDetails

        } catch (e: Exception) {
            println(e.message)
        }
        return null
    }

    suspend fun getTVCredits(id: Int): TVCredits? {

        println("GETTING CREDITS FOR $id")

        if (creditsCache.containsKey(id)) {
            return creditsCache[id]
        }

        val api = TmdbApi.getInstance()
        try {
            val credits = api.getTVCredits(
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

    fun clearSearchResults() {
        searchResults.clear()
    }
}