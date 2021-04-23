package com.android.kit.resource

import android.app.Activity
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Context.getColorResource(name: String): Int {
    return resources.getIdentifier(name, "color", packageName)
}

fun Context.getColor(name: String): Int {
    return ContextCompat.getColor(this, getColorResource(name))
}

fun Context.getColor(res: Int): Int {
    return ContextCompat.getColor(this, res)
}

fun Activity.getColorResource(name: String): Int {
    return resources.getIdentifier(name, "color", packageName)
}

fun Activity.getColor(name: String): Int {
    return ContextCompat.getColor(this, getColorResource(name))
}

fun Fragment.getColorResource(name: String): Int {
    return requireContext().getColorResource(name)
}

fun Fragment.getColor(name: String): Int {
    return requireContext().getColor(name)
}