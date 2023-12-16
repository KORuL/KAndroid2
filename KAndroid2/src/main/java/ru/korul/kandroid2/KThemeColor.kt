package ru.korul.kandroid2

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt

@ColorInt
fun Context.getColorFromAttr(
    @AttrRes attrColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int? {
    return if (theme.resolveAttribute(attrColor, typedValue, resolveRefs))
        typedValue.data
    else
        null
}
