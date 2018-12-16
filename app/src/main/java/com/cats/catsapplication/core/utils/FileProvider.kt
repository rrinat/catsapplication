package com.cats.catsapplication.core.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.support.v4.content.FileProvider
import com.cats.catsapplication.BuildConfig
import okhttp3.ResponseBody
import okio.Okio
import java.io.File
import java.io.IOException


class FileProvider (private val context: Context) {

    fun getDownloadsFolder(): File? {
        val state = Environment.getExternalStorageState()
        return if (Environment.MEDIA_MOUNTED == state) {
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        } else null
    }

    fun getUriForFile(file: File): Uri {
        return FileProvider.getUriForFile(context, BuildConfig.FILES_AUTHORITY, file)
    }

    @Throws(IOException::class)
    fun writeResponseBodyToFile(body: ResponseBody, output: File) {
        if (output.exists()) {
            output.delete()
        }
        output.createNewFile()

        val bufferedSink = Okio.buffer(Okio.sink(output))
        bufferedSink.writeAll(body.source())
        bufferedSink.close()
    }
}