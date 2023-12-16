package ru.korul.kandroid2

import timber.log.Timber

fun Any.printStackTrace(message: String = "") {
    Timber.d("Start stackTrace from $this")
    Throwable(message).printStackTrace()
}