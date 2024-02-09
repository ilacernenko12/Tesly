package com.example.presentation.util

import android.app.Activity
import android.os.SystemClock
import android.view.View
import androidx.fragment.app.Fragment
import dagger.hilt.android.qualifiers.ApplicationContext

// Время задержки между нажатиями (в миллисекундах)
private const val DEFAULT_CLICK_DELAY = 2000L

// Ключ последнего нажатия
private var lastClickTime: Long = 0

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

// Реализация расширения для установки "безопасного" обработчика кликов на View
fun View.setOnSafeClickListener(delay: Long = DEFAULT_CLICK_DELAY, onClick: (View) -> Unit) {
    setOnClickListener {
        val currentTime = SystemClock.elapsedRealtime()
        if (currentTime - lastClickTime > delay) {
            lastClickTime = currentTime
            onClick(it)
        }
    }
}