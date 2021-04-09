package com.android.kit.ui.recyclerview

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

object RecyclerViewUtil {
    fun calculateVerticalSpace(
        parent: RecyclerView,
        view: View,
        includeEdge: Boolean,
        spacing: Int
    ):Array<Int> {
        val spanCount = when (parent.layoutManager) {
            is GridLayoutManager -> (parent.layoutManager as GridLayoutManager).spanCount
            is StaggeredGridLayoutManager -> (parent.layoutManager as StaggeredGridLayoutManager).spanCount
            else -> 1
        }

        val position = parent.getChildAdapterPosition(view) // item position
        val column = if (parent.layoutManager is StaggeredGridLayoutManager) {
            (view.layoutParams as StaggeredGridLayoutManager.LayoutParams).spanIndex
        } else {
            position % spanCount // item column
        }

        var top = 0
        var right = 0
        var left = 0
        var bottom = 0

        if (includeEdge) {
            left =
                spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
            right =
                (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                top = spacing
            }
            bottom = spacing // item bottom
        } else {
            left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
            right =
                spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                top = spacing // item top
            }
        }
        return arrayOf(left, top, right, bottom)
    }
}