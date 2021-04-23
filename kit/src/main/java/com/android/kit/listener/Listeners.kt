package com.android.kit.listener

import android.view.View


typealias EventListener = () -> Unit

typealias DataEventListener<T> = (data: T) -> Unit

typealias SimpleItemClickListener<T> = (item: T, position: Int) -> Unit

typealias ItemClickListener<T> = (item: T, position: Int, view: View) -> Unit