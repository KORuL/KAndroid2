package ru.korul.kandroid2

import java.util.Calendar
import java.util.Calendar.HOUR_OF_DAY
import java.util.Calendar.MILLISECOND
import java.util.Calendar.MINUTE
import java.util.Calendar.SECOND

fun Calendar.atEndOfDay() {
    this[HOUR_OF_DAY] = 23
    this[MINUTE] = 59
    this[SECOND] = 59
    this[MILLISECOND] = 999
}

fun Calendar.atHalfOfDay() {
    this[HOUR_OF_DAY] = 12
    this[MINUTE] = 0
    this[SECOND] = 0
    this[MILLISECOND] = 0
}

fun Calendar.atHalfOfHour() {
    this[MINUTE] = if (this[MINUTE] < 30) 0 else 30
    this[SECOND] = 0
    this[MILLISECOND] = 0
}

fun Calendar.atStartOfDay() {
    this[HOUR_OF_DAY] = 0
    this[MINUTE] = 0
    this[SECOND] = 0
    this[MILLISECOND] = 0
}

fun Calendar.copy(): Calendar {
    return clone() as Calendar
}