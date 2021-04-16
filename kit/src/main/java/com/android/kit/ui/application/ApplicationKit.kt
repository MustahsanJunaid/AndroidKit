package com.android.kit.ui.application

import android.app.Application
import com.android.kit.preference.PreferenceKit

abstract class ApplicationKit : Application() {

    override fun onCreate() {
        super.onCreate()
        PreferenceKit.init(this)
    }

}