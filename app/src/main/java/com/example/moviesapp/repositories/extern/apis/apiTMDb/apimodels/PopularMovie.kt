package com.example.moviesapp.repositories.extern.apis.apiTMDb.apimodels

import com.google.gson.annotations.SerializedName

data class PopularMovie(
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var resultMovies: MutableList<ResultMovie>,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResults: Int
)

