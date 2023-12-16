package ru.korul.kandroid2

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.StrictMode
import android.provider.MediaStore
import androidx.core.content.FileProvider
import timber.log.Timber
import java.io.File

fun Context.sendFileByEmail(file: File) {
    runCatching {
        if (Build.VERSION.SDK_INT >= 24) {
            runCatching {
                val m = StrictMode::class.java.getMethod("disableDeathOnFileUriExposure")
                m.invoke(null)
            }.onFailure { e ->
                Timber.e(e)
            }
        }

        val intent = android.content.Intent(android.content.Intent.ACTION_SEND)
        val extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString())
        val mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        if (extension.equals("", ignoreCase = true) || mimetype == null) {
            // if there is no extension or there is no definite mimetype, still try to open the file
            intent.type = "application/*"
        } else {
            intent.type = mimetype
        }
        intent.putExtra(
            android.content.Intent.EXTRA_SUBJECT, this.getString(R.string.mail_subject)
        )
        this.packageManager.resolveContentProvider(
            "${this.packageName}.provider", PackageManager.GET_META_DATA
        )
        val uri = FileProvider.getUriForFile(
            this, this.applicationContext.packageName + ".provider", file
        )
        this.grantUriPermission(this.packageName, uri, android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION)

        intent.putExtra(android.content.Intent.EXTRA_STREAM, uri)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        intent.addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)

        this.startActivity(
            android.content.Intent.createChooser(
                intent, this.getString(R.string.export_chooser_title)
            )
        )
    }.onFailure { e ->
        Timber.e(e)
    }
}

fun Context.sendFileByEmail(uri: Uri) {
    runCatching {
        if (Build.VERSION.SDK_INT >= 24) {
            runCatching {
                val m = StrictMode::class.java.getMethod("disableDeathOnFileUriExposure")
                m.invoke(null)
            }.onFailure { e ->
                Timber.e(e)
            }
        }

        val intent = android.content.Intent(android.content.Intent.ACTION_SEND)
        val extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(uri.toString())
        val mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        if (extension.equals("", ignoreCase = true) || mimetype == null) {
            // if there is no extension or there is no definite mimetype, still try to open the file
            intent.type = "application/*"
        } else {
            intent.type = mimetype
        }
        intent.putExtra(
            android.content.Intent.EXTRA_SUBJECT, this.getString(R.string.mail_subject)
        )
        this.packageManager.resolveContentProvider(
            "${this.packageName}.provider", PackageManager.GET_META_DATA
        )
        this.grantUriPermission(this.packageName, uri, android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION)

        intent.putExtra(android.content.Intent.EXTRA_STREAM, uri)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        intent.addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)

        this.startActivity(
            android.content.Intent.createChooser(
                intent, this.getString(R.string.export_chooser_title)
            )
        )
    }.onFailure { e ->
        Timber.e(e)
    }
}