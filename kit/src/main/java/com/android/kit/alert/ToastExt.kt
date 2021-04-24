package com.android.kit.alert

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Context.toastShort(@StringRes messageRes: Int) {
    toastShort(getString(messageRes))
}

fun Context.toastShort(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toastLong(@StringRes messageRes: Int) {
    toastLong(getString(messageRes))
}

fun Context.toastLong(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Fragment.toastShort(@StringRes messageRes: Int) {
    toastShort(getString(messageRes))
}

fun Fragment.toastShort(message: String) {
    context?.toastShort(message)
}

fun Fragment.toastLong(@StringRes messageRes: Int) {
    toastLong(getString(messageRes))
}

fun Fragment.toastLong(message: String) {
    context?.toastLong(message)
}
