package com.example.moviesapp.repositories.extern.okhttp

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

class OkHttpClient : IOkHttpClient {
    override fun getOkHttpClient(api_key: String): OkHttpClient {
        var interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        val client: OkHttpClient.Builder = OkHttpClient.Builder()
            .cache(null)
            .addInterceptor(interceptor)
            .addInterceptor {
                return@addInterceptor getInterceptor(api_key, it)
            }

        return client.build()
    }

    override fun getInterceptor(api_key: String, chain: Interceptor.Chain): Response {
        var url = chain.request().url().newBuilder()
            .addQueryParameter("api_key", api_key)
            .build()

        val request = chain.request().newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }



}