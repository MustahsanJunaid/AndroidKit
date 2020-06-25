package com.mustahsan.androidkit.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

fun Context.hasPermission(permission: String): Boolean {

    if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {
        return true
    }
    return ActivityCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_GRANTED
}

fun Fragment.requestPermissionWithRationale(
    permission: String,
    requestCode: Int,
    requirePermission: () -> Unit
) {
    val provideRationale = shouldShowRequestPermissionRationale(permission)
    if (provideRationale) {
        requirePermission()
    } else {
        requestPermissions(arrayOf(permission), requestCode)
    }
}
