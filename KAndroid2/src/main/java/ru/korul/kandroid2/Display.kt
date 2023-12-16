package ru.korul.kandroid2

import android.os.Build
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity


inline val AppCompatActivity.displayMetrics: DisplayMetrics
    get() {
        val outMetrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val display = this.display
            @Suppress("DEPRECATION")
            display?.getRealMetrics(outMetrics)
        } else {
            @Suppress("DEPRECATION")
            val display = this.windowManager.defaultDisplay
            @Suppress("DEPRECATION")
            display.getMetrics(outMetrics)
        }
        return outMetrics
    }
