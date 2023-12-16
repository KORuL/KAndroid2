package ru.korul.kandroid2

import androidx.recyclerview.widget.RecyclerView

fun <T : RecyclerView?> T?.removeItemDecorations() {
    while ((this?.itemDecorationCount ?: 0) > 0) {
        this?.removeItemDecorationAt(0)
    }
}

fun <T : RecyclerView?> T?.addItemDecorationIfNone(decor: RecyclerView.ItemDecoration) {
    if ((this?.itemDecorationCount ?: 0) == 0) {
        this?.addItemDecoration(decor)
    }
}