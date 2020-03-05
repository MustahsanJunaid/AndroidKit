package com.mustahsan.androidkit.log

import android.util.Log
import com.mustahsan.androidkit.BuildConfig
import java.lang.Exception

fun Any.logV(log: String) {
    if (BuildConfig.DEBUG) {
        Log.v(this::class.java.simpleName, log)
    }
}

fun Any.logE(log: String) {
    if (BuildConfig.DEBUG) {
        Log.e(this::class.java.simpleName, log)
    }
}

fun Any.logE(log: String, e: Exception?) {
    if (BuildConfig.DEBUG) {
        Log.e(this::class.java.simpleName, log, e)
    }
}

fun Any.logD(log: String) {
    if (BuildConfig.DEBUG) {
        Log.d(this::class.java.simpleName, log)
    }
}

fun Any.logI(log: String) {
    if (BuildConfig.DEBUG) {
        Log.i(this::class.java.simpleName, log)
    }
}

fun Any.logW(log: String) {
    if (BuildConfig.DEBUG) {
        Log.w(this::class.java.simpleName, log)
    }
}

fun Any.logV(tag: String, log: String) {
    if (BuildConfig.DEBUG) {
        Log.v(tag, log)
    }
}

fun Any.logE(tag: String, log: String) {
    if (BuildConfig.DEBUG) {
        Log.e(tag, log)
    }
}

fun Any.logD(tag: String, log: String) {
    if (BuildConfig.DEBUG) {
        Log.d(tag, log)
    }
}

fun Any.logI(tag: String, log: String) {
    if (BuildConfig.DEBUG) {
        Log.i(tag, log)
    }
}

fun Any.logW(tag: String, log: String) {
    if (BuildConfig.DEBUG) {
        Log.w(tag, log)
    }
}