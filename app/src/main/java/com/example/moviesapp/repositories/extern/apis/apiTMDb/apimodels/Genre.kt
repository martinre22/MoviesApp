package com.example.moviesapp.repositories.extern.apis.apiTMDb.apimodels

import com.google.gson.annotations.SerializedName

class Genre(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String
)