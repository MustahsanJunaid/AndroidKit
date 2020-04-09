package com.mustahsan.androidkit.ktx

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.mustahsan.androidkit.util.MathUtils
import java.io.File


fun Context.openUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = url.toUri()
    startActivity(intent)
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


fun Context.getDrawableResource(name: String): Int {
    return resources.getIdentifier(name, "drawable", packageName)
}

fun Context.getColorResource(name: String): Int {
    return resources.getIdentifier(name, "color", packageName)
}

fun Context.getColor(name: String): Int {
    return ContextCompat.getColor(this, getColorResource(name))
}