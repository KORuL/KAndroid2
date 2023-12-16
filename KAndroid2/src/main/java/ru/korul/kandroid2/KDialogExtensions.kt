package ru.korul.kandroid2

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Add items in the [AlertDialog].
 *
 * @param resArray array with the items
 * @param func HFO called when a item is clicked
 */
fun MaterialAlertDialogBuilder.items(
    @ArrayRes resArray: Int,
    func: MaterialAlertDialogBuilder.(item: Int) -> Unit
) {
    setItems(resArray) { _, item -> func(item) }
}

/**
 * Create a custom [AlertDialog].
 *
 * @param titleRes dialog title
 * @param message dialog description message
 * @param builder HFO to build dialog with custom parameters
 *
 * @return an instance of [AlertDialog]
 */
fun Context.dialog(
    @StringRes titleRes: Int,
    @StringRes message: Int? = null,
    builder: MaterialAlertDialogBuilder.() -> Unit
): MaterialAlertDialogBuilder =
    MaterialAlertDialogBuilder(this).apply {
        setTitle(titleRes)
        message?.let { setMessage(it) }
        builder()
    }

/**
 * Sets the dialog view.
 *
 * @param viewRes the layout resource
 */
fun MaterialAlertDialogBuilder.view(@LayoutRes viewRes: Int) {
    setView(viewRes)
}
