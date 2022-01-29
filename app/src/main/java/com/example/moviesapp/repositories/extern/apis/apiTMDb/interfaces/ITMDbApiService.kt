package com.example.moviesapp.repositories.extern.apis.apiTMDb.interfaces

import com.example.moviesapp.repositories.extern.apis.apiTMDb.apimodels.MovieResponse
import com.example.moviesapp.repositories.extern.apis.apiTMDb.apimodels.PopularMovie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ITMDbApiService {



    @GET("popular")
    fun getPopularMovies(@Query("page") page: String,
                         @Query("language") language: String) : Call<PopularMovie>

    @GET("{id}")
    fun getMovieDetails(@Path("id") idMovie: Int, @Query("language") language: String) : Call<MovieResponse>

    @POST("{movie_id}/rating")
    fun setRateMovie(@Path("movie_id") idMovie: Int, @Query("api_key") api_key: String)
}