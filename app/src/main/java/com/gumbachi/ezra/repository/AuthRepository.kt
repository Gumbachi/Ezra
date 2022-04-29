package com.gumbachi.ezra.repository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.gumbachi.ezra.TMDB_KEY
import com.gumbachi.ezra.data.remote.TmdbApi

object AuthRepository {

    var tmdbToken: String? = null
    var tmdbSessionID: String? by mutableStateOf(null)


    suspend fun generateTMDBToken() {
        val api = TmdbApi.getInstance()
        try {
            val requestToken = api.getRequestToken(apiKey = TMDB_KEY)
            println(requestToken)
            if (requestToken.success) {
                tmdbToken = requestToken.request_token
            }
        }
        catch (e: Exception) {
            println(e.message)
        }
    }

    suspend fun getTMDBSession() {

        if (tmdbToken == null) {
            println("NO TOKEN TO GET SESSION")
            return
        }

        val api = TmdbApi.getInstance()
        try {
            println("Attempting Session Request with $tmdbToken")
            val session = api.createSession(
                apiKey = TMDB_KEY,
                token = tmdbToken!!
            )
            println(session)
            if (session.success) {
                tmdbSessionID = session.session_id
            }
        }
        catch (e: Exception) {
            println("ERROR ${e.message} ${e.cause}")
        }
    }
}