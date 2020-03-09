package com.mustahsan.androidkit.ktx

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit


fun AppCompatActivity.replaceFragment(containerId: Int, fragment: Fragment) {
    supportFragmentManager.commit(allowStateLoss = false){
        replace(containerId, fragment)
    }
}

val Activity.appName: String
    get() = (this as Context).appName

fun Activity.restert(){
    finish()
    startActivity(intent)
}
