package com.gumbachi.ezra.data.remote

import com.gumbachi.ezra.data.remote.responses.movie.MovieCredits
import com.gumbachi.ezra.data.remote.responses.movie.MovieSearchResults
import com.gumbachi.ezra.data.remote.responses.tmdbauth.RequestToken
import com.gumbachi.ezra.data.remote.responses.tv.TVSearchResults
import com.gumbachi.ezra.data.remote.responses.movie.MovieDetails
import com.gumbachi.ezra.data.remote.responses.tmdbauth.Session
import com.gumbachi.ezra.data.remote.responses.tv.TVCredits
import com.gumbachi.ezra.data.remote.responses.tv.TVDetails
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface TmdbApi {

    @GET("authentication/token/new")
    suspend fun getRequestToken(@Query("api_key") apiKey: String): RequestToken

    @FormUrlEncoded
    @POST("authentication/session/new")
    suspend fun createSession(
        @Field("request_token") token: String,
        @Query("api_key") apiKey: String,
    ): Session

    @GET("search/movie?language=en-US")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
    ): MovieSearchResults

    @GET("search/tv?language=en-US")
    suspend fun searchTV(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
    ): TVSearchResults

    @GET("movie/{id}?language=en-US")
    suspend fun getMovieDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
    ): MovieDetails

    @GET("movie/{id}/credits?language=en-US")
    suspend fun getMovieCredits(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
    ): MovieCredits

    @GET("tv/{id}?language=en-US")
    suspend fun getTVDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
    ): TVDetails

    @GET("tv/{id}/credits?language=en-US")
    suspend fun getTVCredits(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
    ): TVCredits


    companion object {
        private var TMDBApi: TmdbApi? = null
        fun getInstance() : TmdbApi {
            if (TMDBApi == null) {
                TMDBApi = Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(TmdbApi::class.java)
            }
            return TMDBApi!!
        }
    }
}



// "https://api.themoviedb.org/3/search/movie?api_key=$apiKey&language=en-US&query=$query&page=1"