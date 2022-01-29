package com.example.moviesapp.repositories.extern.okhttp

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

interface IOkHttpClient {

    fun getOkHttpClient(api_key: String): OkHttpClient

    fun getInterceptor(api_key: String, chain: Interceptor.Chain): Response
}