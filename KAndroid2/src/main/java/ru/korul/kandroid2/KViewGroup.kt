package ru.korul.kandroid2

import android.view.ViewGroup

inline val ViewGroup.views
    get() = (0..<childCount).map { getChildAt(it) }