package ru.korul.kandroid2

import android.graphics.Bitmap

inline fun <T : Bitmap?, R> T.use(block: (T) -> R): R {
    var recycled = false
    try {
        return block(this)
    } catch (e: Exception) {
        recycled = true
        try {
            this?.recycle()
        } catch (_: Exception) {
        }
        throw e
    } finally {
        if (!recycled) {
            this?.recycle()
        }
    }
}