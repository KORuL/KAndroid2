package ru.korul.kandroid2.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

interface IProgress {
    suspend fun show()
    suspend fun cancel()
}

fun <T> deferedJobWithProgress(
    coroutineScope: CoroutineScope,
    delay: Duration = 200.milliseconds,
    progress: IProgress,
    onDeferredResult: suspend (T) -> Unit,
    block: suspend () -> T
) {
    val deferred = coroutineScope.async(Dispatchers.IO) {
        block.invoke()
    }

    coroutineScope.launch(Dispatchers.Main) {
        withContext(Dispatchers.IO) { delay(delay) }

        // check if the task is still active
        if (deferred.isActive) {
            try {
                progress.show()
                // suspend the coroutine till deferred finishes its task
                // on completion, deferred result will be posted to the
                // function and try block will be exited.
                val result = withContext(Dispatchers.IO) { deferred.await() }
                progress.cancel()
                onDeferredResult(result)
            } finally {
                // when deferred finishes and exits try block finally
                // will be invoked and we can cancel the progress dialog
                progress.cancel()
            }
        } else {
            // if deferred completed already withing the wait time, skip
            // showing the progress dialog and post the deferred result
            val result = withContext(Dispatchers.IO) { deferred.await() }
            onDeferredResult(result)
        }
    }
}