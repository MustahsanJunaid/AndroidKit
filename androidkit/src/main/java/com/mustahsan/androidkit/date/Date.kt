package com.mustahsan.androidkit.date

import com.mustahsan.androidkit.date.DatePattern.TIME_LONG24
import com.mustahsan.androidkit.date.DatePattern.TIME_SHORT24
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DatePattern {
    const val NUMBER_WITH_DASH = "dd-MM-yyyy"
    const val NUMBER_WITH_SPACE = "dd MM yyyy"
    const val NUMBER_WITH_DOT = "dd.MM.yyyy"
    const val NUMBER_WITH_SLASH = "dd/MM/yyyy"
    const val SIMPLE_ALPHANUMERIC1 = "dd, MMM yyyy"
    const val SIMPLE_ALPHANUMERIC2 = "MMM dd, yyyy"

    const val TIME_SHORT24 = "hh:mm"
    const val TIME_LONG24 = "hh:mm:ss"
}

fun Date.shortTime(locale: Locale = Locale.US): String {
    val simpleDateFormat = DateFormat.getTimeInstance(DateFormat.SHORT, locale)
    return simpleDateFormat.format(this)
}

fun Date.shortTime24(locale: Locale = Locale.US): String {
    return string(TIME_SHORT24, locale)
}

fun Date.longTime(locale: Locale = Locale.US): String {
    val simpleDateFormat = DateFormat.getTimeInstance(DateFormat.LONG, locale)
    return simpleDateFormat.format(this)
}

fun Date.longTime24(locale: Locale = Locale.US): String {
    return string(TIME_LONG24, locale)
}

fun Date.fullTime(locale: Locale = Locale.US): String {
    val simpleDateFormat = DateFormat.getTimeInstance(DateFormat.FULL, locale)
    return simpleDateFormat.format(this)
}

fun Date.shortDate(locale: Locale = Locale.US): String {
    val simpleDateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale)
    return simpleDateFormat.format(this)
}

fun Date.mediumDate(locale: Locale = Locale.US): String {
    val simpleDateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale)
    return simpleDateFormat.format(this)
}

fun Date.longDate(locale: Locale = Locale.US): String {
    val simpleDateFormat = DateFormat.getDateInstance(DateFormat.LONG, locale)
    return simpleDateFormat.format(this)
}

fun Date.fullDateTime(locale: Locale = Locale.US): String {
    val simpleDateFormat = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, locale)
    return simpleDateFormat.format(this)
}

fun Date.string(format: String, locale: Locale = Locale.US): String {
    val simpleDateFormat = SimpleDateFormat(format, locale)
    return simpleDateFormat.format(this)
}