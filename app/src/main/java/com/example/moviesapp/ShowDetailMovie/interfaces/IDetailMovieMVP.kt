package com.example.moviesapp.ShowDetailMovie.interfaces

import com.example.moviesapp.repositories.extern.apis.apiTMDb.apimodels.Genre
import com.example.moviesapp.repositories.extern.apis.apiTMDb.apimodels.MovieResponse
import com.example.moviesapp.repositories.extern.apis.apiTMDb.interfaces.APIListenerDetailsMovie


interface IDetailMovieMVP {
    interface view{
        fun showTitleMovie(title :String)
        fun showOverviewMovie(overView: String)
        fun showGenresMovie(genres: String)
        fun showReleaseDateMovie(releaseDate: String)
        fun showRateMovie(rate: String)
        fun showError(msg: String)
        fun setUpActionBar()
    }

    interface presenter{
        fun showMovieDetails(idMovie: Int)
        fun showError(msg: String)
        fun getGenres(listGenre: MutableList<Genre>): String
        fun errorCodeShow(code: Int)
        fun showMovieDetailsLocalStorage(movieResponse: MovieResponse)
    }

    interface model{
        fun getMovieDetails(idMovie: Int, listener: APIListenerDetailsMovie)
    }
}