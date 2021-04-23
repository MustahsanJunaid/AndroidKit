package com.android.kit.ui.activity

import android.app.Activity
import android.content.Context
import com.android.kit.ktx.appName

val Activity.appName: String
    get() = (this as Context).appName