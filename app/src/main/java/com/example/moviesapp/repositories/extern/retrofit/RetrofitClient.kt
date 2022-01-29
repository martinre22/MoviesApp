package com.example.moviesapp.repositories.extern.retrofit

import com.example.moviesapp.repositories.extern.okhttp.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient : IRetrofit {
    override fun getRetrofitInstance(api_key: String,baseUrl: String): Retrofit {
        val client = OkHttpClient().getOkHttpClient(api_key)
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}