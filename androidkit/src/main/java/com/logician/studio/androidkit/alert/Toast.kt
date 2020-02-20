package com.logician.studio.androidkit.alert

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment


fun Context.toastShort(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toasShort(message: String){
    context?.toastShort(message)
}

fun Context.toastLong(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Fragment.toastLong(message: String){
    context?.toastLong(message)
}
