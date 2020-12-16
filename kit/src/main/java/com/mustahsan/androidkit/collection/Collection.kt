package com.mustahsan.androidkit.collection

fun <T> MutableList<T>.mapReplace(transform: (T) -> T) {
    for (i in this.indices) {
        this[i] = transform(this[i])
    }
}