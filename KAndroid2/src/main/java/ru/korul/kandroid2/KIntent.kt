package ru.korul.kandroid2

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

inline fun <reified T> Intent(context: Context, builderAction: Intent.() -> Unit = {}): Intent {
    return Intent(context, T::class.java).apply(builderAction)
}

inline fun Intent(action: String?, builderAction: Intent.() -> Unit): Intent {
    return Intent(action).apply(builderAction)
}

inline fun Intent(builderAction: Intent.() -> Unit): Intent {
    return Intent().apply(builderAction)
}

inline fun <reified T : Any> IntentFor(context: Context): Intent = Intent(context, T::class.java)

fun Intent.start(context: Context) = context.startActivity(this)

@Suppress("DEPRECATION")
fun Intent.startForResult(activity: AppCompatActivity, requestCode: Int) = activity.startActivityForResult(this, requestCode)

@Suppress("DEPRECATION")
fun Intent.startForResult(fragment: Fragment, requestCode: Int) = fragment.startActivityForResult(this, requestCode)

fun WebIntent(url: String): Intent =
    if (Patterns.WEB_URL.matcher(url).matches()) {
        Intent(Intent.ACTION_VIEW, Uri.parse(url))
    } else {
        throw IllegalArgumentException("Passed url: $url does not match URL pattern.")
    }