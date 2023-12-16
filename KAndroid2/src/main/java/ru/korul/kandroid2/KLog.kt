@file:Suppress("NOTHING_TO_INLINE")

package ru.korul.kandroid2

import android.util.Log
import timber.log.Timber

fun Any.v(msg: () -> String) {
    if (Log.isLoggable(tag, Log.VERBOSE)) v(msg())
}

fun Any.d(msg: () -> String) {
    if (Log.isLoggable(tag, Log.DEBUG)) d(msg())
}

fun Any.i(msg: () -> String) {
    if (Log.isLoggable(tag, Log.INFO)) i(msg())
}

fun Any.e(msg: () -> String) {
    if (Log.isLoggable(tag, Log.ERROR)) e(msg())
}

fun Any.wtf(msg: () -> String) = w(msg())

fun Any.v(msg: String) = v(tag, msg)

fun Any.d(msg: String) = d(tag, msg)

fun Any.i(msg: String) = i(tag, msg)

fun Any.w(msg: String) = w(tag, msg)

fun Any.e(msg: String) = e(tag, msg)

fun Any.wtf(msg: String) = wtf(tag, msg)

inline fun v(tag: String, msg: String) = Timber.tag(tag).v(msg)

inline fun d(tag: String, msg: String) = Timber.tag(tag).d(msg)

inline fun i(tag: String, msg: String) = Timber.tag(tag).i(msg)

inline fun w(tag: String, msg: String) = Timber.tag(tag).w(msg)

inline fun e(tag: String, msg: String) = Timber.tag(tag).e(msg)

inline fun wtf(tag: String, msg: String) = Timber.tag(tag).wtf(msg)

private val Any.tag: String
    get() = javaClass.simpleName