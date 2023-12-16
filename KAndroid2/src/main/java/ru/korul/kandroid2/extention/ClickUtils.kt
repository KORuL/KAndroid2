package ru.korul.kandroid2.extention

import android.view.View

fun View.clickWithDebounce(debounceTime: Long = 300L, action: (v: View) -> Unit) {
    setOnClickListener { v ->
        when {
            tag != null && (tag as Long) > System.currentTimeMillis() -> return@setOnClickListener
            else -> {
                tag = System.currentTimeMillis() + debounceTime
                action(v)
            }
        }
    }
}
