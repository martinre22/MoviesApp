package com.example.moviesapp.repositories.extern.glide

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.utils.classes.DataConnection

class GlideController: IGlide {

    override fun loadPosterMovie(
        context: Context,
        url: String?,
        placeHolder: Int,
        imgError: Int,
        container: ImageView
    ) {
        Glide.with(context)
            .load("${DataConnection.TMDBAPI_POSTER_GLIDE_BASE_URL.value}$url")
            .placeholder(placeHolder)
            .fallback(R.drawable.ic_broken_image)
            .error(R.drawable.ic_broken_image)
            .skipMemoryCache(true)
            .fitCenter()
            .into(container)

    }

}