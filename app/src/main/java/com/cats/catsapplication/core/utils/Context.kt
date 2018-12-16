package com.cats.catsapplication.core.utils

import android.content.Context
import android.content.Intent
import android.net.Uri


fun Context.makeViewFileIntent(uri: Uri, mimeType: String): Intent? {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.setDataAndType(uri, mimeType)
    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

    return if (existExternalAppForIntent(intent)) intent else null
}

fun Context.existExternalAppForIntent(intent: Intent): Boolean {
    return packageManager.queryIntentActivities(intent, 0)?.isNotEmpty() == true
}