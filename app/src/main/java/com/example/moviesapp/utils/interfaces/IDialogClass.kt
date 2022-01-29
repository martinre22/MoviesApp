package com.example.moviesapp.utils.interfaces

import android.content.Context
import androidx.appcompat.app.AlertDialog


interface IDialogClass {
    fun createDialog(context: Context, movieTitle :String): AlertDialog.Builder
}