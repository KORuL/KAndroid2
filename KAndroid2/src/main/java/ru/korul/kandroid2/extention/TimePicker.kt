package ru.korul.kandroid2.extention

import android.os.Build
import android.widget.TimePicker

fun TimePicker.setHourCompat(currentHour: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        hour = currentHour
    } else {
        @Suppress("DEPRECATION")
        this.currentHour = currentHour
    }
}

fun TimePicker.setMinuteCompat(currentMinutes: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        minute = currentMinutes
    } else {
        @Suppress("DEPRECATION")
        this.currentMinute = currentMinutes
    }
}