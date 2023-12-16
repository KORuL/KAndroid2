package ru.korul.kandroid2


fun Double.isNaNInf(): Boolean {
    return this.isNaN() || this.isInfinite()
}