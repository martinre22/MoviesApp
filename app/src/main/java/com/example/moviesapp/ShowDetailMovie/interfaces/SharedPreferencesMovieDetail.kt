package com.example.moviesapp.ShowDetailMovie.interfaces

import com.example.moviesapp.repositories.extern.apis.apiTMDb.apimodels.MovieResponse

interface SharedPreferencesMovieDetail {
    fun existMovieSharedPreferences(key: String): Boolean
    fun saveMovieSharedPreferences(response: MovieResponse)
    fun getMovieSharedPreferences(idMovie: Int)
}