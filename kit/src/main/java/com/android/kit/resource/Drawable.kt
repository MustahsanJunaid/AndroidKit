package com.android.kit.resource

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


fun Context.getDrawableResource(name: String): Int {
    return resources.getIdentifier(name, "drawable", packageName)
}

fun Context.getDrawableCompat(@DrawableRes res: Int): Drawable? {
    return ContextCompat.getDrawable(this, res)
}

fun Context.getDrawableCompat(resName: String): Drawable? {
    val res = getDrawableResource(resName)
    return ContextCompat.getDrawable(this, res)
}

fun Fragment.getDrawableCompat(@DrawableRes res: Int): Drawable? {
    return context?.getDrawableCompat(res)
}

fun Fragment.getDrawableCompat(resName: String): Drawable? {
    return context?.let { safeContext ->
        val res = safeContext?.getDrawableResource(resName)
        ContextCompat.getDrawable(safeContext, res)
    }
}