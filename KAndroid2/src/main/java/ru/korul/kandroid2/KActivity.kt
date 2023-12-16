@file:Suppress("NOTHING_TO_INLINE")

package ru.korul.kandroid2

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity

@Deprecated("Use findViewById() instead", ReplaceWith("findViewById()"))
inline fun <reified T : View> AppCompatActivity.find(@IdRes id: Int): T = findViewById(id)

@Suppress("DEPRECATION")
inline fun <reified T : Any> AppCompatActivity.startActivityForResult(requestCode: Int, options: Bundle? = null, action: String? = null) =
    startActivityForResult(IntentFor<T>(this).setAction(action), requestCode, options)

inline fun Activity.lockCurrentScreenOrientation() {
    requestedOrientation = when (resources.configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        else -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
    }
}

inline fun Activity.unlockScreenOrientation() {
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
}

fun Activity.setShowWhenLockedCompat(show: Boolean) {
    when {
        Build.VERSION.SDK_INT >= 27 -> setShowWhenLocked(show)
        show -> @Suppress("DEPRECATION") {
            window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
        }
        else -> @Suppress("DEPRECATION") {
            window.clearFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
        }
    }
}
fun Activity.setTurnScreenOnCompat(turn: Boolean) {
    if (Build.VERSION.SDK_INT >= 27) {
        setTurnScreenOn(turn)
    } else @Suppress("DEPRECATION") if (turn) {
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
    } else {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
    }
}

fun Activity.requestDismissKeyguard() {
    if (Build.VERSION.SDK_INT >= 26) {
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        keyguardManager.requestDismissKeyguard(this, null)
    } else @Suppress("DEPRECATION") {
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)
    }
}