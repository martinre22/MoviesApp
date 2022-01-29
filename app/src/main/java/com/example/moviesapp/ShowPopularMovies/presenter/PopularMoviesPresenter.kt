package com.example.moviesapp.ShowPopularMovies.presenter

import android.content.Context
import android.widget.ImageButton
import com.example.moviesapp.ShowPopularMovies.interfaces.IPopularMoviesMVP
import com.example.moviesapp.ShowPopularMovies.model.PopularMoviesModel
import com.example.moviesapp.repositories.extern.apis.apiTMDb.apimodels.PopularMovie
import com.example.moviesapp.repositories.extern.apis.apiTMDb.interfaces.APIListenerPopularMovie
import com.example.moviesapp.utils.classes.DataConnection
import retrofit2.Response

class PopularMoviesPresenter(private val context: Context, private val view: IPopularMoviesMVP.view,
    ) : IPopularMoviesMVP.presenter, APIListenerPopularMovie {

    private var model: IPopularMoviesMVP.model = PopularMoviesModel(context, this)
    private var language: String = DataConnection.LAN_SPANISH.value


    private var actualPage: Int = 1
    private val initialPage: Int = 1
    private val totalPages: Int = 500


    override fun showActualPage() {
        view.showActualPage(actualPage)
    }



    override fun buttonNextAndPreviousPageIsEnabled() {
        when (actualPage) {
            initialPage -> view.imageButtonPreviousPageIsEnabled(false)
            totalPages -> view.imageButtonNextPageIsEnabled(false)
            else -> {
                view.imageButtonNextPageIsEnabled(true)
                view.imageButtonPreviousPageIsEnabled(true)
            }
        }
    }


    override fun showError(msg: String) {
        view.showError(msg)
    }


    override fun showPopularMovies() {
        if (view != null)
        {
            model.getPopularMovies(actualPage.toString(), language, this)

        }
    }

    override fun nextPageButtonClicked(imageButton: ImageButton) {
        if (view != null)
        {
            imageButton.setOnClickListener{
                actualPage++
                model.getPopularMovies(actualPage.toString(), language, this)
            }
        }else{
        }
    }

    override fun previousPageButtonClicked(imageButton: ImageButton) {
        if(view != null)
        {
            imageButton.setOnClickListener {
                actualPage--
                model.getPopularMovies(actualPage.toString(), language, this)
            }
        }else{

        }
    }


    override fun errorCodeShow(code: Int) {
        when(code){
            401 -> view.showError("Acceso denegado. Credenciales invalidas.")
            404 -> view.showError("Pagina no encontrada.")
            else -> view.showError("Servicio no disponible.")
        }
    }

    override fun setLanguage(language: String){
        this.language = language
    }



    override fun onSuccess(response: Response<PopularMovie>) {
        var listPopularMovies = response.body()?.resultMovies!!
        actualPage = response.body()?.page!!


        view.showPopularMoviesView(listPopularMovies)

        buttonNextAndPreviousPageIsEnabled()
        showActualPage()
    }

    override fun onError(response: Response<PopularMovie>) {
        view.imageButtonPreviousPageIsEnabled(false)
        view.imageButtonNextPageIsEnabled(false)
        errorCodeShow(response.code())
    }

    override fun onFailure(t: Throwable) {
        view.showError(t.message.toString())
    }


}