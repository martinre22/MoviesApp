package com.example.moviesapp.ShowPopularMovies.view

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.ShowPopularMovies.adapters.PopularMovieAdapter
import com.example.moviesapp.ShowPopularMovies.interfaces.IPopularMoviesMVP
import com.example.moviesapp.ShowPopularMovies.presenter.PopularMoviesPresenter
import com.example.moviesapp.R
import com.example.moviesapp.ShowDetailMovie.view.DetailMovieView
import com.example.moviesapp.ShowPopularMovies.interfaces.ListenerOnClick
import com.example.moviesapp.databinding.ActivityPopularMoviesViewBinding
import com.example.moviesapp.repositories.extern.apis.apiTMDb.apimodels.ResultMovie
import com.example.moviesapp.utils.classes.DataConnection
import com.example.moviesapp.utils.classes.DialogClass



class PopularMoviesView : AppCompatActivity(), IPopularMoviesMVP.view, ListenerOnClick {

    private lateinit var presenter: IPopularMoviesMVP.presenter
    private lateinit var menu: Menu
    private var flag = 0

    private var listPopularMovies = mutableListOf<ResultMovie>()

    private lateinit var adapter : PopularMovieAdapter

    private lateinit var binding: ActivityPopularMoviesViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPopularMoviesViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = PopularMoviesPresenter(this, this)

        adapter = PopularMovieAdapter(this, this)

        presenter.nextPageButtonClicked(binding.imageButtonNextPagePopularMoviesView)
        presenter.previousPageButtonClicked(binding.imageButtonPreviousPagePopularMoviesView)
        presenter.showPopularMovies()

    }


    override fun showPopularMoviesView(listMovies: MutableList<ResultMovie>){
        listPopularMovies.clear()
        listPopularMovies.addAll(listMovies)
        adapter.setData(listPopularMovies)
        setRecyclerView(adapter)

    }

    override fun showActualPage(page: Int) {
        binding.textViewActualPagePopularMoviesView.text = page.toString()
    }

    override fun toActivityDetailMovie(id: String) {
        var i = Intent(this, DetailMovieView::class.java)
        i.putExtra("movieId", id)
        startActivity(i)
    }

    override fun setRecyclerView(adapter: PopularMovieAdapter) {
        binding.recyclerViewPopularMoviesView.setHasFixedSize(true)
        binding.recyclerViewPopularMoviesView.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPopularMoviesView.adapter = adapter
    }

    override fun showDialog(id: String, title: String) {
        var iDialogClass = DialogClass().createDialog(this, title)
        iDialogClass.setPositiveButton("Ver Detalle", DialogInterface.OnClickListener {
                dialogInterface, i -> toActivityDetailMovie(id)  })
        iDialogClass.setNegativeButton("Cancelar", DialogInterface.OnClickListener {
                dialogInterface, i ->  })
        iDialogClass.create().show()
    }

    override fun getIdMovie(movieId: String, title:String) {
        showDialog(movieId, title)
    }




    override fun showError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }


    override fun imageButtonPreviousPageIsEnabled(value: Boolean) {
        binding.imageButtonPreviousPagePopularMoviesView.isEnabled = value
    }

    override fun imageButtonNextPageIsEnabled(value: Boolean) {
        binding.imageButtonNextPagePopularMoviesView.isEnabled = value
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menu != null) {
            this.menu = menu
        }
        menuInflater.inflate(R.menu.menu_language, this.menu)
        val item = menu!!.findItem(R.id.item_search_movie)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(filterString: String?): Boolean {
                adapter.filter.filter(filterString)
                return true
            }

            override fun onQueryTextChange(filterString: String?): Boolean {
                adapter.filter.filter(filterString)
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

            return when(item.itemId){
                R.id.item_menu_flag_language -> {
                    flag++
                    if (flag%2 == 0) {
                        menu.getItem(1).icon = resources.getDrawable(R.drawable.spanish24)
                        presenter.setLanguage(DataConnection.LAN_SPANISH.value)
                        presenter.showPopularMovies()
                    }else {
                        menu.getItem(1).icon = resources.getDrawable(R.drawable.english24)
                        presenter.setLanguage(DataConnection.LAN_ENGLISH.value)
                        presenter.showPopularMovies()
                    }
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
    }

}