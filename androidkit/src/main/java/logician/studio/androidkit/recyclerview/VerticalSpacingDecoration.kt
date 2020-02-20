package logician.studio.androidkit.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class VerticalSpacingDecoration(
    private val spanCount: Int,
    private val spacing: Int,
    private val includeEdge: Boolean
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val spacing = calculateVerticalSpace(parent, view)
        outRect.set(spacing[0],spacing[1],spacing[2],spacing[3])
    }

    private fun calculateVerticalSpace(
        parent: RecyclerView,
        view: View
    ):Array<Int> {
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