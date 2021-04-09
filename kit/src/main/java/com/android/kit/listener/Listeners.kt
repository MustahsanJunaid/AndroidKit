package com.android.kit.listener

import android.view.View


typealias AnyEventListener = () -> Unit

typealias SimpleRecyclerItemClickListener<T> = (item: T) -> Unit

typealias RecyclerItemClickListener<T> = (item: T, position: Int, view: View) -> Unit