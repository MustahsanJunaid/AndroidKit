package com.android.kit.ktx

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.provider.Settings
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.android.kit.util.MathUtils
import java.io.File


fun Context.openUri(uri: String, action: String = Intent.ACTION_VIEW) {
    val intent = Intent(action)
    intent.data = uri.toUri()
    startActivity(intent)
}

fun Context.openStore() {
    return try {
        startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
        )
    } catch (anfe: ActivityNotFoundException) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )
        )
    }
}

fun Context.startActivity(mClass: Class<*>) {
    startActivity(Intent(this, mClass))
}

fun Context.isOnline(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = connectivityManager.activeNetworkInfo
    return netInfo != null && netInfo.isConnected
}

fun Context.share(file: File, type: String, message: String? = null) {
    val uri = FileProvider.getUriForFile(this, applicationContext.packageName + ".provider", file)
    share(uri, type, message)
}

fun Context.share(uri: Uri, type: String, message: String? = null) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.putExtra(Intent.EXTRA_STREAM, uri)
    if (!message.isNullOrEmpty()) {
        intent.putExtra(Intent.EXTRA_TEXT, message)
    }
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.type = type
    startActivity(intent)
//    startActivity(Intent.createChooser(intent, "Share"))
}

fun Context.share(message: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.putExtra(Intent.EXTRA_TEXT, message)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.type = "text/plain"
    startActivity(intent)
//    startActivity(Intent.createChooser(intent, "Share"))
}

val Context.activity: Activity?
    get() {
        var mContext = this
        while (mContext is ContextWrapper) {
            if (mContext is Activity) {
                return mContext
            }
            mContext = mContext.baseContext
        }
        return null
    }

val Context.appName: String
    get() {
        val applicationInfo = applicationInfo
        val stringId = applicationInfo.labelRes
        return if (stringId == 0) applicationInfo.nonLocalizedLabel.toString() else getString(
            stringId
        )
    }

fun Context.openSettings() {
    val intent =
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.parse("package:$packageName")
    startActivity(intent)
}

val Context.screenWidth: Int
    get() = resources.displayMetrics.widthPixels

val Context.screenHeight: Int
    get() = resources.displayMetrics.heightPixels

val Context.screenRatio: Pair<Float, Float>
    get() {
        val w = screenWidth
        val h = screenHeight
        return MathUtils.ratio(w, h)
    }