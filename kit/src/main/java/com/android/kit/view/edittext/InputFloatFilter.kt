package com.android.kit.view.edittext

import android.text.InputFilter
import android.text.Spanned

class InputFloatFilter(private var min: Float, private var max: Float) : InputFilter {

    constructor(min: String, max: String) : this(min.toFloat(),max.toFloat())

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        try {
            val input = (dest.toString() + source.toString()).toFloat()
            if (isInRange(min, max, input))
                return null
        } catch (nfe: NumberFormatException) {
        }

        return ""
    }

    private fun isInRange(a: Float, b: Float, c: Float): Boolean {
        return if (b > a) c in a..b else c in b..a
    }
}