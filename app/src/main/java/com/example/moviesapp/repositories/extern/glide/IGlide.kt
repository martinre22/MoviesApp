package com.example.moviesapp.repositories.extern.glide

import android.content.Context
import android.widget.ImageView

interface IGlide {
    fun loadPosterMovie(context: Context, url: String?, placeHolder: Int, imgError: Int, container: ImageView)
}