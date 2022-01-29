package com.example.moviesapp.utils.classes

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.moviesapp.utils.interfaces.IDialogClass

class DialogClass() : IDialogClass {
    override fun createDialog(context: Context, movieTitle: String): AlertDialog.Builder {
        var dialog = AlertDialog.Builder(context)
        dialog.setTitle("Movies App")
        dialog.setMessage("Pelicula \n" +
                "${movieTitle.uppercase()}")
        return dialog
    }
}