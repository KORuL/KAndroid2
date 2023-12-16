package ru.korul.kandroid2.coroutines

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


// запрещено запускать из CoroutineScope блока
context(Fragment)
inline fun <T> Flow<T>.launchAndCollectIn(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline action: suspend CoroutineScope.(T) -> Unit
) = lifecycleScope.launch {
    repeatOnLifecycle(minActiveState) {
        collect {
            action(it)
        }
    }
}

// запрещено запускать из CoroutineScope блока
context(AppCompatActivity)
inline fun <T> Flow<T>.launchAndCollectIn(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline action: suspend CoroutineScope.(T) -> Unit
) = lifecycleScope.launch {
    repeatOnLifecycle(minActiveState) {
        collect {
            action(it)
        }
    }
}

inline fun Fragment.launchAndRepeatWithViewLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline completionBlock: () -> Unit = {},
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(minActiveState) {
            block()
        }
    }.invokeOnCompletion { completionBlock() }
}

inline fun AppCompatActivity.launchAndRepeatWithViewLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline completionBlock: () -> Unit = {},
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(minActiveState) {
            block()
        }
    }.invokeOnCompletion { completionBlock() }
}

