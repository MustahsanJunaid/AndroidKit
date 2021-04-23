package com.android.kit.number

import android.content.Context
import android.util.DisplayMetrics
import kotlin.random.Random


fun Int.toPx(context: Context): Int {
    return this * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}

fun Int.toDp(context: Context): Int {
    return this / (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}

val Int.random: Int
    get() = Random.nextInt(this)
