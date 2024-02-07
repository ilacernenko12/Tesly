package com.example.presentation.util

import android.app.Activity
import android.view.View
fun Activity.visible(view: View) {
    view.visibility = View.VISIBLE
}

fun Activity.gone(view: View) {
    view.visibility  = View.GONE
}