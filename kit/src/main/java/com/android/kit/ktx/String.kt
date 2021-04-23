package com.android.kit.ktx

import android.net.Uri

val String.titleCase: String
    get() {
        val titleCase = StringBuilder()
        var nextTitleCase = true

        for (c in this.toCharArray()) {
            val character = when {
                Character.isSpaceChar(c) -> {
                    nextTitleCase = true
                    c
                }
                nextTitleCase -> {
                    nextTitleCase = false
                    Character.toTitleCase(c)
                }
                else -> {
                    c
                }
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