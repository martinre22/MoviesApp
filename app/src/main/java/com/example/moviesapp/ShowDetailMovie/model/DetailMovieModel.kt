package com.example.moviesapp.ShowDetailMovie.model

import android.content.Context
import android.preference.PreferenceManager
import com.example.moviesapp.ShowDetailMovie.interfaces.IDetailMovieMVP
import com.example.moviesapp.ShowDetailMovie.interfaces.SharedPreferencesMovieDetail
import com.example.moviesapp.repositories.extern.apis.apiTMDb.apimodels.MovieResponse
import com.example.moviesapp.repositories.extern.apis.apiTMDb.interfaces.APIListenerDetailsMovie
import com.example.moviesapp.repositories.extern.apis.apiTMDb.interfaces.ITMDbApiService
import com.example.moviesapp.repositories.extern.retrofit.RetrofitClient
import com.example.moviesapp.utils.classes.DataConnection
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response

class DetailMovieModel(private var context:Context, private var presenter: IDetailMovieMVP.presenter)
    : IDetailMovieMVP.model, SharedPreferencesMovieDetail {



    override fun getMovieDetails(idMovie: Int, listener: APIListenerDetailsMovie) {

        var id = idMovie
        if (existMovieSharedPreferences(id.toString())){
            getMovieSharedPreferences(id)
        }else{
            val retrofitOb = RetrofitClient().getRetrofitInstance(
                DataConnection.API_KEY.value,
                DataConnection.TMDBAPI_RETROFIT_BASE_URL.value)
            val service = retrofitOb.create<ITMDbApiService>(ITMDbApiService::class.java)
            var call = service.getMovieDetails(id, "es-ES")
            call.enqueue(object: retrofit2.Callback<MovieResponse>{
                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    if (response.isSuccessful) {
                        listener.onSuccess(response)
                        response.body()?.let { saveMovieSharedPreferences(it) }
                    }else {
                        listener.onError(response)
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    listener.onFailure(t)
                }

            })
        }

    }

    override fun existMovieSharedPreferences(key: String): Boolean {
        val appSharedPrefs = PreferenceManager
            .getDefaultSharedPreferences(context)
        return appSharedPrefs.contains(key)
    }

    override fun saveMovieSharedPreferences(response: MovieResponse) {
        val appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val prefEditor = appSharedPrefs.edit()
        val gson = Gson()
        val json = gson.toJson(response)
        prefEditor.putString(response.id.toString(), json)
        prefEditor.commit()
    }

    override fun getMovieSharedPreferences(idMovie: Int) {
        val appSharedPrefs = PreferenceManager
            .getDefaultSharedPreferences(context)
        val gson = Gson()

        val json = appSharedPrefs.getString(idMovie.toString(), "")
        val movie = gson.fromJson(json, MovieResponse::class.java)
        presenter.showMovieDetailsLocalStorage(movie)
    }
}