package ru.korul.kandroid2

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.database.Cursor
import android.graphics.drawable.Drawable
import android.view.KeyEvent
import android.view.View
import android.widget.ListAdapter
import androidx.annotation.ArrayRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Fragment.alert(
    message: String,
    title: String? = null,
    init: (KAlertDialogBuilder.() -> Unit)? = null
) = requireActivity().alert(message, title, init)

fun Context.alert(
    message: String,
    title: String? = null,
    init: (KAlertDialogBuilder.() -> Unit)? = null
) = KAlertDialogBuilder(this).apply {
    if (title != null) title(title)
    message(message)
    if (init != null) init()
}

fun Fragment.alert(
    @StringRes message: Int,
    @StringRes title: Int? = null,
    init: (KAlertDialogBuilder.() -> Unit)? = null
) = requireActivity().alert(message, title, init)

fun Context.alert(
    @StringRes message: Int,
    @StringRes title: Int? = null,
    init: (KAlertDialogBuilder.() -> Unit)? = null
) = KAlertDialogBuilder(this).apply {
    if (title != null) title(title)
    message(message)
    if (init != null) init()
}

fun Fragment.alert(init: KAlertDialogBuilder.() -> Unit): KAlertDialogBuilder = requireActivity().alert(init)

fun Context.alert(init: KAlertDialogBuilder.() -> Unit) = KAlertDialogBuilder(this).apply { init() }

@Suppress("DEPRECATION")
fun Fragment.progressDialog(
    @StringRes message: Int? = null,
    @StringRes title: Int? = null,
    init: (ProgressDialog.() -> Unit)? = null
) = requireActivity().progressDialog(message, title, init)

@Suppress("DEPRECATION")
fun Context.progressDialog(
    @StringRes message: Int? = null,
    @StringRes title: Int? = null,
    init: (ProgressDialog.() -> Unit)? = null
) = progressDialog(false, message?.let { getString(it) }, title?.let { getString(it) }, init)

@Suppress("DEPRECATION")
fun Fragment.indeterminateProgressDialog(
    @StringRes message: Int? = null,
    @StringRes title: Int? = null,
    init: (ProgressDialog.() -> Unit)? = null
) = requireActivity().progressDialog(message, title, init)

@Suppress("DEPRECATION")
fun Context.indeterminateProgressDialog(
    @StringRes message: Int? = null,
    @StringRes title: Int? = null,
    init: (ProgressDialog.() -> Unit)? = null
) = progressDialog(true, message?.let { getString(it) }, title?.let { getString(it) }, init)

@Suppress("DEPRECATION")
fun Fragment.progressDialog(
    message: String? = null,
    title: String? = null,
    init: (ProgressDialog.() -> Unit)? = null
) = requireActivity().progressDialog(message, title, init)

@Suppress("DEPRECATION")
fun Context.progressDialog(
    message: String? = null,
    title: String? = null,
    init: (ProgressDialog.() -> Unit)? = null
) = progressDialog(false, message, title, init)

@Suppress("DEPRECATION")
fun Fragment.indeterminateProgressDialog(
    message: String? = null,
    title: String? = null,
    init: (ProgressDialog.() -> Unit)? = null
) = requireActivity().indeterminateProgressDialog(message, title, init)

@Suppress("DEPRECATION")
fun Context.indeterminateProgressDialog(
    message: String? = null,
    title: String? = null,
    init: (ProgressDialog.() -> Unit)? = null
) = progressDialog(true, message, title, init)

@Suppress("DEPRECATION")
private fun Context.progressDialog(
    indeterminate: Boolean,
    message: String? = null,
    title: String? = null,
    init: (ProgressDialog.() -> Unit)? = null
) = ProgressDialog(this).apply {
    isIndeterminate = indeterminate
    if (!indeterminate) setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
    if (message != null) setMessage(message)
    if (title != null) setTitle(title)
    if (init != null) init()
    show()
}

fun Fragment.selector(
    title: CharSequence? = null,
    items: List<CharSequence>,
    onClick: (Int) -> Unit
) = requireActivity().selector(title, items, onClick)

fun Context.selector(
    title: CharSequence? = null,
    items: List<CharSequence>,
    onClick: (Int) -> Unit
) {
    with(KAlertDialogBuilder(this)) {
        if (title != null) title(title)
        items(items, onClick)
        show()
    }
}

class KAlertDialogBuilder(private val ctx: Context) {

    val builder: MaterialAlertDialogBuilder = MaterialAlertDialogBuilder(ctx)
    var dialog: AlertDialog? = null

    fun dismiss() = dialog?.dismiss()

    fun show(): KAlertDialogBuilder {
        dialog = builder.create()
        dialog!!.show()
        return this
    }

    fun title(title: CharSequence) {
        builder.setTitle(title)
    }

    fun title(@StringRes resource: Int) {
        builder.setTitle(resource)
    }

    fun message(title: CharSequence) {
        builder.setMessage(title)
    }

    fun message(@StringRes resource: Int) {
        builder.setMessage(resource)
    }

    fun icon(@DrawableRes icon: Int) {
        builder.setIcon(icon)
    }

    fun icon(icon: Drawable) {
        builder.setIcon(icon)
    }

    fun customTitle(title: View) {
        builder.setCustomTitle(title)
    }

    fun customView(view: View) {
        builder.setView(view)
    }

    fun cancellable(value: Boolean = true) {
        builder.setCancelable(value)
    }

    fun onCancel(f: () -> Unit) {
        builder.setOnCancelListener { f() }
    }

    fun onKey(f: (keyCode: Int, e: KeyEvent) -> Boolean) {
        builder.setOnKeyListener { _, keyCode, event -> f(keyCode, event) }
    }

    fun neutralButton(@StringRes textResource: Int = android.R.string.ok, f: DialogInterface.() -> Unit = { dismiss() }) {
        neutralButton(ctx.getString(textResource), f)
    }

    private fun neutralButton(title: String, f: DialogInterface.() -> Unit = { dismiss() }) {
        builder.setNeutralButton(title) { dialog, _ -> dialog.f() }
    }

    fun positiveButton(@StringRes textResource: Int = android.R.string.ok, f: DialogInterface.() -> Unit) {
        positiveButton(ctx.getString(textResource), f)
    }

    private fun positiveButton(title: String, f: DialogInterface.() -> Unit) {
        builder.setPositiveButton(title) { dialog, _ -> dialog.f() }
    }

    fun negativeButton(@StringRes textResource: Int = android.R.string.cancel, f: DialogInterface.() -> Unit = { dismiss() }) {
        negativeButton(ctx.getString(textResource), f)
    }

    private fun negativeButton(title: String, f: DialogInterface.() -> Unit = { dismiss() }) {
        builder.setNegativeButton(title) { dialog, _ -> dialog.f() }
    }

    fun items(@ArrayRes itemsId: Int, f: (which: Int) -> Unit) {
        items(ctx.resources!!.getTextArray(itemsId), f)
    }

    fun items(items: List<CharSequence>, f: (which: Int) -> Unit) {
        items(items.toTypedArray(), f)
    }

    fun items(items: Array<CharSequence>, f: (which: Int) -> Unit) {
        builder.setItems(items) { _, which -> f(which) }
    }

    fun adapter(adapter: ListAdapter, f: (which: Int) -> Unit) {
        builder.setAdapter(adapter) { _, which -> f(which) }
    }

    fun adapter(cursor: Cursor, labelColumn: String, f: (which: Int) -> Unit) {
        builder.setCursor(cursor, { _, which -> f(which) }, labelColumn)
    }
}