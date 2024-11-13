package com.example.movieapp.network

import com.example.movieapp.models.MovieDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiManager {
    @GET("Search/{movieName}")
    suspend fun getMoviesList(
        @Path("movieName") category: String,
        @Query("rapidapi-key") apiKey: String = API_KEY
    ): List<MovieDetails>

    @GET("FindByImbdId/{imdbId}")
    suspend fun getMovieDetails(
        @Path("imdbId") imdbId: String,
        @Query("rapidapi-key") apiKey: String = API_KEY
    ): MovieDetails

    companion object {
        const val BASE_URL = "https://moviedatabase8.p.rapidapi.com/"
        const val API_KEY = "3743ade113msh085c2f7bd57e950p1136e1jsndf936a0e2f59"


    }
}







