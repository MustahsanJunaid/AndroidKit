package com.android.kit

import android.util.Log
import java.lang.Exception

fun Any.logV(log: String) {
    logV(this::class.java.simpleName, log)
}

fun Any.logE(log: String) {
    logE(this::class.java.simpleName, log)
}

fun Any.logD(log: String) {
    logD(this::class.java.simpleName, log)
}

fun Any.logI(log: String) {
    logI(this::class.java.simpleName, log)
}

fun Any.logWtf(log: String) {
    logWtf(this::class.java.simpleName, log)
}

fun Any.logW(log: String) {
    Log.w(this::class.java.simpleName, log)
}

fun Any.logV(tag: String, log: String) {
    Log.v(tag, log)
}

fun Any.logE(log: String, e: Exception?) {
    logE(this::class.java.simpleName, log, e)
}

fun Any.logE(tag: String, log: String) {
    Log.e(tag, log)
}

fun Any.logE(tag: String, log: String, e: Exception?) {
    Log.e(tag, log, e)
}

fun Any.logD(tag: String, log: String) {
    Log.d(tag, log)
}

fun Any.logI(tag: String, log: String) {
    Log.i(tag, log)
}

fun Any.logW(tag: String, log: String) {
    Log.w(tag, log)
}

fun Any.logWtf(tag: String, log: String) {
    Log.wtf(tag, log)
}