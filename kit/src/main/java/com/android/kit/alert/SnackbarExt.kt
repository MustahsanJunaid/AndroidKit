package com.android.kit.alert

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.snackBarShort(str:String): Snackbar {
    val snackBar = Snackbar.make(this,str, Snackbar.LENGTH_SHORT)
    snackBar.show()
    return snackBar
}

fun View.snackBarShort(msgId:Int): Snackbar {
    val snackBar = Snackbar.make(this,msgId, Snackbar.LENGTH_SHORT)
    snackBar.show()
    return snackBar
}

fun View.snackBarLong(str:String): Snackbar {
    val snackBar = Snackbar.make(this,str, Snackbar.LENGTH_LONG)
    snackBar.show()
    return snackBar
}

fun View.snackBarLong(msgId:Int): Snackbar {
    val snackBar = Snackbar.make(this,msgId, Snackbar.LENGTH_LONG)
    snackBar.show()
    return snackBar
}