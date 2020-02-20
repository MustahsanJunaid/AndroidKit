package com.logician.studio.androidkit.ktx

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


fun AppCompatActivity.replaceFragment(containerId: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().replace(containerId, fragment).commit()
}

val Activity.appName: String
    get() = (this as Context).appName

fun Activity.restert(){
    finish()
    startActivity(intent)
}
