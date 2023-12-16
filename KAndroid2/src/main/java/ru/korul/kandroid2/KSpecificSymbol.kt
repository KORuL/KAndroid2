package ru.korul.kandroid2

fun String.removeSpecificSymbol() = replace(Regex("[+^'%$&*#@!\"]*"), "")