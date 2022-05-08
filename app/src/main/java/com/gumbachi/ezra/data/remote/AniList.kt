package com.gumbachi.ezra.data.remote

import com.apollographql.apollo3.ApolloClient

val AniListAPI = ApolloClient.Builder()
    .serverUrl("https://graphql.anilist.co/")
    .build()