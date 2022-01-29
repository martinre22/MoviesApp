package com.example.moviesapp.repositories.extern.apis.apiTMDb.interfaces

import com.example.moviesapp.repositories.extern.apis.apiTMDb.apimodels.PopularMovie
import retrofit2.Response

interface APIListenerPopularMovie {
    fun onSuccess(response: Response<PopularMovie>)
    fun onError(response: Response<PopularMovie>)
    fun onFailure(t: Throwable)
}