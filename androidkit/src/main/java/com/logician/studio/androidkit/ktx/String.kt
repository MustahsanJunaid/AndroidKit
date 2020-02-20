package com.logician.studio.androidkit.ktx

import android.net.Uri
import android.text.TextUtils
import android.util.Patterns

val String.isValidEmail: Boolean
    get() = TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

val String.isValidPassword: Boolean
    get() = this.trim().length>5


val String.titleCase: String
    get() {
        val titleCase = StringBuilder()
        var nextTitleCase = true

        for (c in this.toCharArray()) {
            val character = if (Character.isSpaceChar(c)) {
                nextTitleCase = true
                c
            } else if (nextTitleCase) {
                nextTitleCase = false
                Character.toTitleCase(c)
            } else {
                c
            }

            titleCase.append(character)
        }
        return titleCase.toString()
    }

fun String.isNumber() = this.matches("-?\\d+(\\.\\d+)?".toRegex())

fun String.toAssetPath(): String {
    return "file:///android_asset/$this"
}

fun String.toAssetUri(): Uri? {
    return Uri.parse(toAssetPath())
}