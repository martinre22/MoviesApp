package com.example.moviesapp.ShowDetailMovie.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.moviesapp.ShowDetailMovie.interfaces.IDetailMovieMVP
import com.example.moviesapp.ShowDetailMovie.presenter.DetailMoviePresenter
import com.example.moviesapp.databinding.ActivityDetailMovieViewBinding
import com.example.moviesapp.repositories.extern.apis.apiTMDb.apimodels.MovieResponse
import com.example.moviesapp.repositories.extern.apis.apiTMDb.interfaces.ITMDbApiService
import com.example.moviesapp.repositories.extern.retrofit.RetrofitClient
import com.example.moviesapp.utils.classes.DataConnection
import retrofit2.Call
import retrofit2.Response

class DetailMovieView : AppCompatActivity(), IDetailMovieMVP.view {


    private lateinit var presenter: IDetailMovieMVP.presenter
    private lateinit var binding: ActivityDetailMovieViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpActionBar()
        presenter = DetailMoviePresenter(this, this)

        var par = this.intent.extras
        if (par != null) {
            var id: String? = par.getString("movieId")
            if (id != null) {
                //Toast.makeText(this, id.toString(), Toast.LENGTH_LONG).show()
                presenter.showMovieDetails(id.toInt())
            }
        }


    }

    private fun showMovie(idMovie: String){
        var id = idMovie.toInt()
        val retrofitOb = RetrofitClient().getRetrofitInstance(
            DataConnection.API_KEY.value,
            DataConnection.TMDBAPI_RETROFIT_BASE_URL.value)
        val service = retrofitOb.create<ITMDbApiService>(ITMDbApiService::class.java)
        var call = service.getMovieDetails(id, "es-ES")
        call.enqueue(object: retrofit2.Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful){
                    var movie = response.body()
                    if (movie != null) {
                        Toast.makeText(applicationContext, "pelicula:" + movie.title
                            + "genero= " + movie.genres[0].name, Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun showTitleMovie(title: String) {
        binding.textViewMovieTitleDetailMovieView.text = title
    }

    override fun showOverviewMovie(overView: String) {
        binding.textViewOverviewDescriptionMovieDetailView.text = overView
    }

    override fun showGenresMovie(genres: String) {
        binding.textViewGenresDescriptionMovieDetailView.text = genres
    }

    override fun showReleaseDateMovie(releaseDate: String) {
        binding.textViewReleaseDescriptionMovieDetailView.text = releaseDate
    }

    override fun showRateMovie(rate: String) {
        binding.textViewRateMovieDetailsMovieView.text = rate
    }

    override fun showError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun setUpActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


}