package ru.korul.kandroid2

import android.text.Editable

fun Editable?.toDouble(def: Double = 0.0): Double = runCatching { this.toString().toDouble(def) }.getOrDefault(def)

fun Editable?.toInt(def: Int = 0): Int = runCatching { this.toString().toInt(def) }.getOrDefault(def)

fun Editable?.toFloat(def: Float = 0f): Float = runCatching { this.toString().toFloat(def) }.getOrDefault(def)

fun CharSequence?.toDouble(def: Double = 0.0): Double = runCatching { this.toString().toDoubleOrNull() ?: def }.getOrDefault(def)

fun CharSequence?.toInt(def: Int = 0): Int = runCatching { this.toString().toIntOrNull() ?: def }.getOrDefault(def)

fun CharSequence?.toFloat(def: Float = 0f): Float = runCatching { this.toString().toFloatOrNull() ?: def }.getOrDefault(def)
