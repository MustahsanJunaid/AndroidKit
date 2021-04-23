package com.android.kit.view

import android.graphics.BlendModeColorFilter
import android.graphics.drawable.Drawable
import android.os.Build
import com.android.kit.ui.utility.FilterHelper

fun Drawable.setColorFilter(color: Int, mode: FilterHelper.Mode) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        colorFilter = BlendModeColorFilter(color, FilterHelper.getBlendMode(mode))
    } else {
        setColorFilter(color, FilterHelper.getPorterDuffMode(mode))
    }
}