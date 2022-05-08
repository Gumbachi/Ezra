package com.gumbachi.ezra.data.remote.responses.tmdbauth

data class RequestToken(
    val expires_at: String,
    val request_token: String,
    val success: Boolean
)