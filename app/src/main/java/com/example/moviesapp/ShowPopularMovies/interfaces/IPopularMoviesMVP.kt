package com.example.moviesapp.ShowPopularMovies.interfaces

import android.widget.ImageButton
import com.example.moviesapp.ShowPopularMovies.adapters.PopularMovieAdapter
import com.example.moviesapp.repositories.extern.apis.apiTMDb.apimodels.ResultMovie
import com.example.moviesapp.repositories.extern.apis.apiTMDb.interfaces.APIListenerPopularMovie

interface IPopularMoviesMVP {

    interface view{
        fun showError(msg: String)
        fun imageButtonPreviousPageIsEnabled(value :Boolean)
        fun imageButtonNextPageIsEnabled(value :Boolean)
        fun showPopularMoviesView(listMovies: MutableList<ResultMovie>)
        fun showActualPage(page: Int)
        fun toActivityDetailMovie(id: String)
        fun setRecyclerView(adapter: PopularMovieAdapter)
        fun showDialog(id: String, title: String)

    }

    interface presenter{
        fun showPopularMovies()
        fun nextPageButtonClicked(imageButton: ImageButton)
        fun previousPageButtonClicked(imageButton: ImageButton)
        fun showActualPage()
        fun buttonNextAndPreviousPageIsEnabled()
        fun showError(msg: String)
        fun errorCodeShow(code: Int)
        fun setLanguage(language: String)

    }

    interface model{

        fun showError(msg: String)
        fun getPopularMovies(
            page: String,
            language: String,
            listenerPopularMovie: APIListenerPopularMovie)
    }


}