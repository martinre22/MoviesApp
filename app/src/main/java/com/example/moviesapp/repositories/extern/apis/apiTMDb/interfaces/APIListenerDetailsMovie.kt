package com.example.moviesapp.repositories.extern.apis.apiTMDb.interfaces

import com.example.moviesapp.repositories.extern.apis.apiTMDb.apimodels.MovieResponse
import retrofit2.Response

interface APIListenerDetailsMovie {
    fun onSuccess(response: Response<MovieResponse>)
    fun onError(response: Response<MovieResponse>)
    fun onFailure(t: Throwable)
}