package com.android.kit.view.seekbar

import android.widget.SeekBar

abstract class SimpleSeekBarListener : SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

}

fun SeekBar.onProgressChanged(function: (progress:Int, fromUser: Boolean) -> Unit) {
    setOnSeekBarChangeListener(object : SimpleSeekBarListener() {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            function(progress, fromUser)
        }
    })
}