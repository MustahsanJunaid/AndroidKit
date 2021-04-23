package com.android.kit.view.textview

import android.widget.TextView

fun TextView.changeCase(case: Int): String? {
    var space = true
    val builder = StringBuilder(this.text)
    val len = builder.length
    when (case) {
        TextCase.SMALL -> {
            for (i in 0 until len) {
                builder.setCharAt(i, Character.toLowerCase(builder[i]))
            }
        }
        TextCase.CAPS -> {
            for (i in 0 until len) {
                builder.setCharAt(i, Character.toUpperCase(builder[i]))
            }
        }
        else -> {
            for (i in 0 until len) {
                val c = builder[i]
                if (space) {
                    if (!Character.isWhitespace(c)) { // Convert to title case and switch out of whitespace mode.
                        builder.setCharAt(i, Character.toTitleCase(c))
                        space = false
                    }
                } else if (Character.isWhitespace(c)) {
                    space = true
                } else {
                    builder.setCharAt(i, Character.toLowerCase(c))
                }
            }
        }
    }
    text = builder.toString()
    return text.toString()
}