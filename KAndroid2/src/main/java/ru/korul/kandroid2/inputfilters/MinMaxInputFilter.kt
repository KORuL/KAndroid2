package ru.korul.kandroid2.inputfilters

import android.text.InputFilter
import android.text.Spanned
import android.widget.EditText

class MinMaxInputFilter(private val min: Int, private val max: Int) : InputFilter {

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        return (dest.toString() + source.toString()).toIntOrNull()?.let {
            if (isInRange(min, max, it))
                null
            else
                ""
        }
    }

    private fun isInRange(min: Int, max: Int, value: Int): Boolean {
        return if (max > min) value in min..max else value in max..min
    }
}

fun EditText.addInputFilterMinMaxInt(minValue: Int, maxValue: Int) {
    filters += MinMaxInputFilter(minValue, maxValue)
}