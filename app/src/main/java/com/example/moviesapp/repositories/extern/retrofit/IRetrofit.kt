package com.example.moviesapp.repositories.extern.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface IRetrofit {
    fun getRetrofitInstance(api_key: String, baseUrl: String): Retrofit
}