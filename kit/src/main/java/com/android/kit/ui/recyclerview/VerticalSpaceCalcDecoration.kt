package com.android.kit.ui.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.android.kit.ui.recyclerview.RecyclerViewUtil.calculateVerticalSpace

class VerticalSpaceCalcDecoration(
    private val spacing: Int,
    private val includeEdge: Boolean = true,
    private val function: (view:View, left:Int, top:Int, right:Int, bottom:Int) -> Unit
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val spacing = calculateVerticalSpace(parent, view, includeEdge, spacing)
        function(view, spacing[0],spacing[1],spacing[2],spacing[3])
    }
}