package com.example.composeexercise.util

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date

object ExtensionFunctions {
    @SuppressLint("SimpleDateFormat")
    fun Context.createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        return File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            externalCacheDir /* directory */
        )
    }

    suspend fun String.isValidUrl(): Boolean{
        return suspendCancellableCoroutine { continuetion ->
            var result = false
            try {
                val url = URL(this)
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()
                val responseCode = connection.responseCode
                val contentType = connection.contentType

                if (responseCode == HttpURLConnection.HTTP_OK && contentType.startsWith("image/")) {
                    result = true
                }
            } catch (e: Exception) {
                Log.d("testt",e.message.toString())
                // Handle exceptions
            }
            continuetion.resumeWith(Result.success(result))
        }
    }
}