package com.mustahsan.androidkit.res

import android.content.Context


fun Context.getDrawableResource(name: String): Int {
    return resources.getIdentifier(name, "drawable", packageName)
}