package com.example.moviesapp.utils.classes

enum class DataConnection (val value: String){
    TMDBAPI_RETROFIT_BASE_URL("https://api.themoviedb.org/3/movie/"),
    API_KEY("4b1d200e1674a7ba3bcc03811ad2f5db"),
    LAN_SPANISH("es-ES"),
    LAN_ENGLISH("en-EN"),
    TMDBAPI_POSTER_GLIDE_BASE_URL("https://image.tmdb.org/t/p/w300/")
}