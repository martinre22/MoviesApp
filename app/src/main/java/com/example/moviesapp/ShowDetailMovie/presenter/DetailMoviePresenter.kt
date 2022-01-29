package com.example.moviesapp.ShowDetailMovie.presenter

import android.content.Context
import android.util.Log
import com.example.moviesapp.ShowDetailMovie.interfaces.IDetailMovieMVP
import com.example.moviesapp.ShowDetailMovie.model.DetailMovieModel
import com.example.moviesapp.repositories.extern.apis.apiTMDb.apimodels.Genre
import com.example.moviesapp.repositories.extern.apis.apiTMDb.apimodels.MovieResponse
import com.example.moviesapp.repositories.extern.apis.apiTMDb.interfaces.APIListenerDetailsMovie
import retrofit2.Response

class DetailMoviePresenter(private var context: Context, private val view: IDetailMovieMVP.view): IDetailMovieMVP.presenter, APIListenerDetailsMovie {

    private var model :IDetailMovieMVP.model = DetailMovieModel(context, this)

    private val TAG = "LoadMovie"


    override fun showMovieDetails(idMovie: Int) {
        model.getMovieDetails(idMovie, this)
    }

    override fun showError(msg: String) {
        view.showError(msg)
    }

    override fun getGenres(listGenre: MutableList<Genre>): String {
        var genres: String = ""

        if (listGenre.size > 1)
        {
            for (g in listGenre) {
                genres += "-" + g.name + "-"
            }
        }else {
            for (g in listGenre)
                genres += g.name
        }

        return genres
    }

    override fun errorCodeShow(code: Int) {
        when(code){
            401 -> view.showError("Acceso denegado. Credenciales invalidas.")
            404 -> view.showError("Pagina no encontrada.")
            else -> view.showError("Servicio no disponible.")
        }
    }

    override fun showMovieDetailsLocalStorage(movieResponse: MovieResponse) {
        var responseMovie = movieResponse
        var title = responseMovie.title
        var overview = responseMovie.overview
        var releaseDate = responseMovie.releaseDate
        var rate = responseMovie.voteAverage
        var genres = responseMovie?.let { getGenres(it.genres) }

        view.showTitleMovie(title)
        view.showOverviewMovie(overview)
        view.showRateMovie(rate.toString())
        view.showReleaseDateMovie(releaseDate)
        view.showGenresMovie(genres)


        Log.d(TAG, "Local" )
    }

    override fun onSuccess(response: Response<MovieResponse>) {
        var responseMovie = response.body()
        var title = responseMovie?.title
        var overview = responseMovie?.overview
        var releaseDate = responseMovie?.releaseDate
        var rate = responseMovie?.voteAverage
        var genres = responseMovie?.let { getGenres(it.genres) }

        view.showTitleMovie(title!!)
        view.showOverviewMovie(overview!!)
        view.showRateMovie(rate!!.toString())
        view.showReleaseDateMovie(releaseDate!!)
        view.showGenresMovie(genres!!)

        Log.d(TAG, "The Movie Database API")
    }

    override fun onError(response: Response<MovieResponse>) {
        errorCodeShow(response.code())
    }

    override fun onFailure(t: Throwable) {
        view.showError(t.message.toString())
    }


}