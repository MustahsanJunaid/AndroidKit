package com.android.kit.ui.recyclerview

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import java.lang.NullPointerException

fun RecyclerView.reachedEnd(delta: Int = 1, reached: () -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val itemCount = recyclerView.adapter?.itemCount ?: 0
            val lastVisibleItemPosition =
                (recyclerView.layoutManager as? LinearLayoutManager)?.findLastVisibleItemPosition()

            if (lastVisibleItemPosition == itemCount - delta) {
                reached()
            }
        }
    })
}

enum class Direction {
    UP,
    DOWN,
    RIGHT,
    LEFT
}

fun RecyclerView.setItemSpacing(spacing: Int, includeEdges: Boolean = true) {
    if (layoutManager == null) {
        throw NullPointerException("set LayoutManager before item spacing")
    }
    val span = when (layoutManager) {
        is GridLayoutManager -> (layoutManager as GridLayoutManager).spanCount
        is StaggeredGridLayoutManager -> (layoutManager as StaggeredGridLayoutManager).spanCount
        else -> 1
    }
    addItemDecoration(
        if (layoutManager!!.canScrollHorizontally()) {
            HorizontalSpacingDecoration(span, spacing, includeEdges)
        } else {
            VerticalSpacingDecoration(span, spacing, includeEdges)
        }
    )
}

fun RecyclerView.scrollDirection(direction: (Direction) -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            direction(
                when {
                    dy > 0 -> Direction.DOWN
                    dy < 0 -> Direction.UP
                    dx > 0 -> Direction.RIGHT
                    else -> Direction.LEFT  // (dx < 0)
                }
            )
        }
    })
}

