package ru.korul.kandroid2

import android.app.Activity
import android.view.View
import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

fun Activity.hideSoftInput() {
    runCatching {
        var view = this.currentFocus
        if (view == null) view = View(this)
        val imm = this.inputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun EditText.showSoftInput() {
    this.isFocusable = true
    this.isFocusableInTouchMode = true
    this.requestFocus()
    val imm = context.inputMethodManager
    imm?.showSoftInput(this, 0)
}

fun Activity.hideKeyboard() {
    val view = currentFocus ?: return
    inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.showKeyboard() {
    val view = currentFocus ?: return
    inputMethodManager?.showSoftInput(view, 0)
}

fun Activity.getRootView(): View? = findViewById(android.R.id.content)

fun Activity.listenToKeyboardVisibilityChanges(listener: OnKeyboardVisibilityChanged) {
    getRootView()?.let {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { _, insets ->
            if (insets.isVisible(WindowInsetsCompat.Type.ime())) listener.onKeyboardVisible() else listener.onKeyboardDismissed()
            insets
        }
    }
}

fun Activity.stopListeningToKeyboardVisibilityChanges() {
    getRootView()?.let {
        ViewCompat.setOnApplyWindowInsetsListener(it, null)
    }
}

interface OnKeyboardVisibilityChanged {
    fun onKeyboardVisible()
    fun onKeyboardDismissed()
}