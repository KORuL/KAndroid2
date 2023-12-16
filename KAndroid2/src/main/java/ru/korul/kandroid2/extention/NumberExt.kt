package ru.korul.kandroid2.extention

import kotlin.math.floor

fun Double.format(digits: Int) = "%.${digits}f".format(this)

fun Float.format(digits: Int) = "%.${digits}f".format(this)

fun Double.fractionalPart() = this - floor(this)

fun Double.hasFractionalPart() = this - floor(this) != 0.0

fun Float.fractionalPart() = this - floor(this)

fun Float.hasFractionalPart() = this - floor(this) != 0f