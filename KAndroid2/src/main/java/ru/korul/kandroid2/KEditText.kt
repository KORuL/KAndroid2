package ru.korul.kandroid2

import android.os.Looper
import android.text.Editable
import android.text.SpannableStringBuilder
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

@OptIn(FlowPreview::class)
fun EditText.afterTextChanged(millisDelay: Long = 300L): Flow<String> {
    return callbackFlow {
        checkMainThread()

        val listener = doOnTextChanged { text, _, _, _ -> trySend(text).isSuccess }
        awaitClose { removeTextChangedListener(listener) }
    }
        .onStart { emit(text) }
        .filterNot { it?.trim() == null }
        .map { it.toString() }
        .distinctUntilChanged()
        .debounce(millisDelay)
}

internal fun checkMainThread() {
    check(Looper.myLooper() == Looper.getMainLooper()) {
        "Expected to be called on the main thread but was " + Thread.currentThread().name
    }
}

fun String.toEditable(): Editable = SpannableStringBuilder(this)

