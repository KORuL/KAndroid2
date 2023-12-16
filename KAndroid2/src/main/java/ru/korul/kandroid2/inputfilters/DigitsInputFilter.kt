package ru.korul.kandroid2.inputfilters

import android.text.InputFilter
import android.text.Spanned
import android.widget.EditText
import timber.log.Timber


fun EditText.addInputFilterMinMaxDouble(mMaxIntegerDigitsLength: Int, mMaxDigitsAfterLength: Int, mMax: Double) {
    filters += DigitsInputFilter(mMaxIntegerDigitsLength, mMaxDigitsAfterLength, mMax)
}

class DigitsInputFilter(private val mMaxIntegerDigitsLength: Int, private val mMaxDigitsAfterLength: Int, private val mMax: Double) : InputFilter {
    private val DOT = "."
    override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
        val allText = runCatching { getAllText(source, dest, dstart) }.onFailure {
            Timber.e("DigitsInputFilter error $source, $dest, $dstart")
        }.getOrNull()
        return if (allText.isNullOrEmpty()) {
            null
        } else {
            val onlyDigitsText = getOnlyDigitsPart(allText)
            val enteredValue: Double = try {
                onlyDigitsText.toDouble()
            } catch (e: NumberFormatException) {
                return ""
            }
            checkMaxValueRule(enteredValue, onlyDigitsText)
        }
    }

    private fun checkMaxValueRule(enteredValue: Double, onlyDigitsText: String): CharSequence? {
        return if (enteredValue > mMax) {
            ""
        } else {
            handleInputRules(onlyDigitsText)
        }
    }

    private fun handleInputRules(onlyDigitsText: String): CharSequence? {
        return if (isDecimalDigit(onlyDigitsText)) {
            checkRuleForDecimalDigits(onlyDigitsText)
        } else {
            checkRuleForIntegerDigits(onlyDigitsText.length)
        }
    }

    private fun isDecimalDigit(onlyDigitsText: String): Boolean {
        return onlyDigitsText.contains(DOT)
    }

    private fun checkRuleForDecimalDigits(onlyDigitsPart: String): CharSequence? {
        val afterDotPart = onlyDigitsPart.substring(onlyDigitsPart.indexOf(DOT), onlyDigitsPart.length - 1)
        return if (afterDotPart.length > mMaxDigitsAfterLength) {
            ""
        } else null
    }

    private fun checkRuleForIntegerDigits(allTextLength: Int): CharSequence? {
        return if (allTextLength > mMaxIntegerDigitsLength) {
            ""
        } else null
    }

    private fun getOnlyDigitsPart(text: String): String {
        return text.replace("[^0-9?!\\.]".toRegex(), "")
    }

    private fun getAllText(source: CharSequence, dest: Spanned, dstart: Int): String {
        var allText = ""
        if (dest.toString().isNotEmpty()) {
            allText = if (source.toString().isEmpty()) {
                deleteCharAtIndex(dest, dstart)
            } else {
                StringBuilder(dest).insert(dstart, source).toString()
            }
        }
        return allText
    }

    private fun deleteCharAtIndex(dest: Spanned, dstart: Int): String {
        val builder = StringBuilder(dest)
        builder.deleteCharAt(dstart)
        return builder.toString()
    }
}