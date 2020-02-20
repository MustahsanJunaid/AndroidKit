package com.android.kit.recyclerview

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.reachedEnd(delta: Int, reached: () -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!canScrollVertically(delta)) {
                reached()
            }
        }
    })
}

enum class Direction{
    UP,
    DOWN,
    RIGHT,
    LEFT
}

fun RecyclerView.scrollDirection(direction: (Direction) -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            direction( when {
                dy > 0 -> Direction.DOWN
                dy < 0 -> Direction.UP
                dx > 0 -> Direction.RIGHT
                else -> Direction.LEFT  // (dx < 0)
            })
        }
    })
}

