package com.android.kit.number

import android.content.res.Resources


val Int.px: Float
    get() = toFloat().px
val Int.dp: Float
    get() = toFloat().dp

val Float.px: Float
    get() = (this / Resources.getSystem().displayMetrics.density)
val Float.dp: Float
    get() = (this * Resources.getSystem().displayMetrics.density)
