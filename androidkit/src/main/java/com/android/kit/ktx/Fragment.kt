package com.android.kit.ktx

import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

fun Fragment.replaceFragment(containerId: Int, fragment: Fragment) {
    if(activity!=null) {
        childFragmentManager.commit(allowStateLoss = false){
            replace(containerId, fragment)
        }
    }
}