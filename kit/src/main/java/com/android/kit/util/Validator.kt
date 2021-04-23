package com.android.kit.util

import android.text.TextUtils
import android.util.Patterns
import java.util.regex.Matcher
import java.util.regex.Pattern

object Validator {
    const val NONE = 0
    const val SIMPLE = 1
    const val MEDIUM = 2
    const val STRONG = 4

    fun isValidPassword(
        password: String,
        strength: Int = MEDIUM,
        minLength: Int = 6
    ): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        if (strength == NONE) return password.length >= minLength
        val regex = when (strength) {
            STRONG -> {
                "^(?=.*[0-9])(?=.*(?i)[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{$minLength,}$"
            }
            MEDIUM -> {
                "^(?=.*[0-9])(?=.*(?i)[A-Z])(?=\\S+$).{$minLength,}$"
            }
            else -> {
                "^(?=.*[0-9]).{$minLength,}$"
            }
        }
        pattern = Pattern.compile(regex)
        matcher = pattern.matcher(password)
        return matcher.matches()
    }

    fun passwordScore(password: String, minLength: Int = 6): Int {
        val strongReg = "^(?=.*[0-9])(?=.*(?i)[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{$minLength,}$"
        val mediumReg = "^(?=.*[0-9])(?=.*(?i)[A-Z])(?=\\S+$).{$minLength,}$"
        val simpleReg = "^(?=.*[0-9]).{$minLength,}$"
        val strongPattern = Pattern.compile(strongReg)
        val mediumPattern = Pattern.compile(mediumReg)
        val simplePattern = Pattern.compile(simpleReg)
        return when {
            strongPattern.matcher(password).matches() -> STRONG
            mediumPattern.matcher(password).matches() -> MEDIUM
            simplePattern.matcher(password).matches() -> SIMPLE
            else -> NONE
        }
    }

    fun passwordStrength(password: String, minLength: Int = 6): String =
        when (passwordScore(password, minLength)) {
            STRONG -> "Strong"
            MEDIUM -> "Medium"
            SIMPLE -> "Simple"
            else -> "Weak"
        }


    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}