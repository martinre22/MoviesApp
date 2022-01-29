package com.example.moviesapp.ShowPopularMovies.model

import android.content.Context
import com.example.moviesapp.ShowPopularMovies.interfaces.IPopularMoviesMVP
import com.example.moviesapp.repositories.extern.apis.apiTMDb.apimodels.PopularMovie
import com.example.moviesapp.repositories.extern.apis.apiTMDb.interfaces.APIListenerPopularMovie
import com.example.moviesapp.repositories.extern.apis.apiTMDb.interfaces.ITMDbApiService
import com.example.moviesapp.repositories.extern.retrofit.RetrofitClient
import com.example.moviesapp.utils.classes.DataConnection
import retrofit2.Call
import retrofit2.Response


class PopularMoviesModel(private var context: Context, private var presenter: IPopularMoviesMVP.presenter)
    : IPopularMoviesMVP.model {

    override fun showError(msg: String) {
        presenter.showError(msg)
    }

    override fun getPopularMovies(page: String,
                                  language: String, listenerPopularMovie: APIListenerPopularMovie)  {

        val retrofitOb = RetrofitClient().getRetrofitInstance(
            DataConnection.API_KEY.value,
            DataConnection.TMDBAPI_RETROFIT_BASE_URL.value)
        val service = retrofitOb.create<ITMDbApiService>(ITMDbApiService::class.java)
        val call = service.getPopularMovies(page, language)
        call.enqueue(object: retrofit2.Callback<PopularMovie>{
            override fun onFailure(call: Call<PopularMovie>, t: Throwable) {
                listenerPopularMovie.onFailure(t)
            }

            override fun onResponse(call: Call<PopularMovie>, response: Response<PopularMovie>) {
                if (response.isSuccessful)
                    listenerPopularMovie.onSuccess(response)
                else
                    listenerPopularMovie.onError(response)

            }
        })
    }



}

