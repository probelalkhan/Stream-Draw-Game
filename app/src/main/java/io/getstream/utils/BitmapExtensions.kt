package io.getstream.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

fun Bitmap.toBase64String(): String? {
    val outputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 85, outputStream)
    return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
}

fun String.toBitmap(): Bitmap? {
    val decodedBytes: ByteArray = Base64.decode(
        this.substring(this.indexOf(",") + 1),
        Base64.DEFAULT
    )
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}
