package com.android.kit.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mustahsan.androidkit.ktx.screenRatio
import com.mustahsan.androidkit.sample.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView.text = "$screenRatio"
    }
}
