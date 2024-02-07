package com.example.presentation.util

import android.app.Activity
import android.view.View
import androidx.fragment.app.Fragment

fun Activity.visible(view: View) {
    view.visibility = View.VISIBLE
}

fun Activity.gone(view: View) {
    view.visibility  = View.GONE
}

fun Fragment.visible(view: View) {
    view.visibility = View.VISIBLE
}

fun Fragment.gone(view: View) {
    view.visibility  = View.GONE
}