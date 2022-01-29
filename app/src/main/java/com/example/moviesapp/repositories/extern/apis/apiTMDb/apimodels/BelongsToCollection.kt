package com.example.moviesapp.repositories.extern.apis.apiTMDb.apimodels

import com.google.gson.annotations.SerializedName

data class BelongsToCollection(@SerializedName("backdrop_path")
                              var backdropPath: String,
                               @SerializedName("id")
                              var id: Int,
                               @SerializedName("name")
                              var name: String,
                               @SerializedName("poster_path")
                              var posterPath: String)