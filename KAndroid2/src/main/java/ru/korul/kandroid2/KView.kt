@file:Suppress("NOTHING_TO_INLINE")

package ru.korul.kandroid2

import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.transition.TransitionManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialElevationScale

@Deprecated("Use findViewById() instead", ReplaceWith("findViewById()"))
inline fun <reified T : View> View.find(@IdRes id: Int): T = findViewById(id)

var View.visible
    get() = visibility == VISIBLE
    set(value) {
        visibility = if (value) VISIBLE else GONE
    }

inline fun View.hide(gone: Boolean = true) {
    visibility = if (gone) GONE else INVISIBLE
}

inline fun View.show() {
    visibility = VISIBLE
}

inline fun View.setWidth(width: Int) {
    val params = layoutParams
    params.width = width
    layoutParams = params
}

inline fun View.setHeight(height: Int) {
    val params = layoutParams
    params.height = height
    layoutParams = params
}

inline fun View.setSize(width: Int, height: Int) {
    val params = layoutParams
    params.width = width
    params.height = height
    layoutParams = params
}

fun View.showSnackbar(message: String) = Snackbar.make(this, message, Snackbar.LENGTH_LONG).apply { show() }

fun View.setVisibilityWithScale(visible: Int) {
    TransitionManager.beginDelayedTransition(parent as ViewGroup, MaterialElevationScale(true))
    visibility = visible
}

fun View.setPaddingRTL(
    left: Int,
    top: Int,
    right: Int,
    bottom: Int,
) {
    setPaddingRelative(left, top, right, bottom)
}

fun View.setRippleBackground() {
    background = AppCompatResources.getDrawable(context, TypedValue().apply {
        context.theme.resolveAttribute(
            android.R.attr.selectableItemBackground, this, true
        )
    }.resourceId)
}

fun View.setRippleForeground() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        foreground = AppCompatResources.getDrawable(context, TypedValue().apply {
            context.theme.resolveAttribute(
                android.R.attr.selectableItemBackground, this, true
            )
        }.resourceId)
    }
}