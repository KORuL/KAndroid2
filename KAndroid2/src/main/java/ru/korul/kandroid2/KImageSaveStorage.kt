package ru.korul.kandroid2

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


fun Bitmap.saveBitmapToStorage(context: Context, fileName: String = ""): Uri? {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures")
        values.put(MediaStore.Images.Media.IS_PENDING, true)
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)

        val uri: Uri? =
            context.applicationContext.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        if (uri != null) {
            saveImageToStream(this, context.applicationContext.contentResolver.openOutputStream(uri))
            values.put(MediaStore.Images.Media.IS_PENDING, false)
            context.applicationContext.contentResolver.update(uri, values, null, null)
            return uri
        }
    } else {
        val extBaseDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
        val file = File(extBaseDir.absolutePath)
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return null
            }
        }

        val f = File(file, fileName)
        runCatching { f.createNewFile() }
        saveImageToStream(this, FileOutputStream(f))
        MediaScannerConnection.scanFile(
            context.applicationContext,
            arrayOf(f.toString()),
            arrayOf(f.name),
            null
        )
        return Uri.fromFile(f)
    }
    return null
}

fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
    if (outputStream != null) {
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream)
            outputStream.close()
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}