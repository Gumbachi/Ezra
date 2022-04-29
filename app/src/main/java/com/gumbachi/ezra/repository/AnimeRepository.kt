package com.gumbachi.ezra.repository

import androidx.compose.runtime.mutableStateListOf
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.gumbachi.ezra.AnimeDetailsQuery
import com.gumbachi.ezra.SearchAnimeQuery
import com.gumbachi.ezra.data.dummy.DummyData
import com.gumbachi.ezra.data.remote.AniListAPI
import com.gumbachi.ezra.model.Anime
import com.gumbachi.ezra.model.WatchStatus

object AnimeRepository {

    private val anime = mutableStateListOf<Anime>()
    private val searchResults = mutableStateListOf<SearchAnimeQuery.Medium>()

    fun remove(id: Int) {
        println("REMOVING ID $id")
        anime.removeIf { id == it.id }
    }

    fun update(updatedMedia: Anime) {
        val index = anime.indexOfFirst { it.id == updatedMedia.id }

        println("Replacing at $index")

        // This is done to force a recomposition
        if (index != -1) {
            anime.removeAt(index)
            anime.add(index, updatedMedia)
        } else {
            anime.add(updatedMedia)
        }

    }

    fun getAnimeByStatus(status: WatchStatus): List<Anime> {
        return anime.filter { status == it.status }
    }

    fun getSearchResults(): List<SearchAnimeQuery.Medium> {
        return searchResults.toList()
    }

    suspend fun searchAnime(query: String) {
        val response = try {
            AniListAPI.query(SearchAnimeQuery(query)).execute()
        } catch (e: ApolloException) {
            println("Anime Search Failed")
            null
        }

        response?.data?.page?.media?.filterNotNull()?.let {
            searchResults.clear()
            searchResults.addAll(it)
        }
    }

    suspend fun getAnimeDetails(id: Int): AnimeDetailsQuery.Media? {
        val response = try {
            AniListAPI.query(AnimeDetailsQuery(id)).execute()
        } catch (e: ApolloException) {
            println("Anime Details Search Failed")
            null
        }

        response?.data?.media?.let {
            return it
        }
        return null
    }

    fun clearSearchResults() {
        searchResults.clear()
    }
}