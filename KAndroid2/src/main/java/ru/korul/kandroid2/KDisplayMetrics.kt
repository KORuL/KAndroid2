@file:Suppress("NOTHING_TO_INLINE")

package ru.korul.kandroid2

import android.content.Context
import android.content.res.Resources
import android.view.View
import androidx.annotation.DimenRes
import androidx.fragment.app.Fragment

inline fun Context.dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()

@Suppress("DEPRECATION")
inline fun Context.sp(value: Int): Int = (value * resources.displayMetrics.scaledDensity).toInt()

inline fun Fragment.dp(value: Int): Int = requireContext().dp(value)

inline fun Fragment.sp(value: Int): Int = requireContext().sp(value)

inline fun View.dp(value: Int): Int = context.dp(value)

inline fun View.sp(value: Int): Int = context.sp(value)

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Float.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Int.dpf: Float
    get() = this * Resources.getSystem().displayMetrics.density + 0.5f

val Float.dpf: Float
    get() = this * Resources.getSystem().displayMetrics.density + 0.5f

fun Context.px(@DimenRes dimen: Int): Int =
    this.resources.getDimension(dimen).toInt()

fun Context.pxf(@DimenRes dimen: Int): Float =
    this.resources.getDimension(dimen)
