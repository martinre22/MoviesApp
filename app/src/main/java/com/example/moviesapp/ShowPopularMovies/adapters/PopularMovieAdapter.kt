package com.example.moviesapp.ShowPopularMovies.adapters

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.ShowPopularMovies.interfaces.ListenerOnClick
import com.example.moviesapp.repositories.extern.glide.GlideController
import com.example.moviesapp.repositories.extern.apis.apiTMDb.apimodels.ResultMovie


class PopularMovieAdapter(var context: Context, private val click: ListenerOnClick) :
    RecyclerView.Adapter<PopularMovieAdapter.ViewHolder>(), Filterable {

    private var listPopularMovies: MutableList<ResultMovie> = mutableListOf()
    private var itemListMovieFilter = mutableListOf<ResultMovie>()




    fun setData(listPopularMovies: MutableList<ResultMovie>){
        this.listPopularMovies = listPopularMovies
        this.itemListMovieFilter = listPopularMovies
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolder(inflater.inflate(R.layout.list_item_movies,
            parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemMovie = listPopularMovies[position]
        holder.bind(itemMovie, context)
    }

    override fun getItemCount(): Int {
        return listPopularMovies.size
    }



    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view),
    View.OnClickListener{

        init {
            itemView.setOnClickListener (this)
        }
        private val titleMovie: TextView = view.findViewById<TextView>(R.id.text_view_movie_title_card_view_movies)
        private val posterMovie: ImageView = view.findViewById<ImageView>(R.id.image_view_poster_movie)


        fun bind(movie: ResultMovie, context: Context){
            val obMovie: ResultMovie = movie
            titleMovie.text = obMovie.title
            if(obMovie.posterPath == ""){
                posterMovie.setImageResource(R.drawable.ic_broken_image)
            }else{
                var glide = GlideController()
                glide.loadPosterMovie(context,
                    obMovie.posterPath,
                    R.drawable.error512,
                    R.drawable.error512,
                    posterMovie)
            }


        }

        override fun onClick(p0: View?) {
            val currentMovie = listPopularMovies[adapterPosition]
            var id = currentMovie.id.toString()
            var title = currentMovie.title
            click.getIdMovie( currentMovie.id.toString(), title)
        }


    }

    override fun getFilter(): Filter {
        return object: Filter(){
            override fun performFiltering(query: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (query == null || query.length < 0){
                    filterResults.count = itemListMovieFilter.size
                    filterResults.values = itemListMovieFilter
                }else{
                    var searchQuery = query.toString().lowercase()
                    val listResultMovies = mutableListOf<ResultMovie>()

                    for (itemMovie in itemListMovieFilter){
                        if (itemMovie.title.lowercase().contains(searchQuery)) {
                            listResultMovies.add(itemMovie)
                        }
                    }
                    filterResults.count = listResultMovies.size
                    filterResults.values = listResultMovies
                }

                return filterResults
            }

            override fun publishResults(query: CharSequence?, filter: FilterResults?) {
                if (filter!!.count != 0)
                {
                    listPopularMovies = filter!!.values as MutableList<ResultMovie>
                }else{
                    listPopularMovies.clear()
                    Toast.makeText(context, "Sin coincidencias.", Toast.LENGTH_LONG).show()
                }

                notifyDataSetChanged()
            }
        }
    }


}