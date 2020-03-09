package com.mustahsan.androidkit.ktx

import android.net.Uri
import android.text.TextUtils
import android.util.Patterns
import java.util.regex.Matcher
import java.util.regex.Pattern

val String.isValidEmail: Boolean
    get() = !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

enum class PasswordStrength {
    LIGHT, MEDIUM, STRONG
}

fun String.isValidPassword(
    strength: PasswordStrength = PasswordStrength.MEDIUM,
    minLength: Int = 8
): Boolean {
    if (length < minLength) return false
    val pattern: Pattern
    val matcher: Matcher
    val regex = when (strength) {
        PasswordStrength.STRONG -> {
            "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
        }
        PasswordStrength.MEDIUM -> {
            "^(?=.*[0-9])(?=.*[A-Z]).{4,}$"
        }
        else -> {
            "^(?=.*[0-9])(?=.*[a-z]).{4,}$"
        }
    }
    pattern = Pattern.compile(regex)
    matcher = pattern.matcher(this)
    return matcher.matches()
}

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