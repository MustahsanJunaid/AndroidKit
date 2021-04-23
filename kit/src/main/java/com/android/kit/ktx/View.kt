package com.android.kit.ktx

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.constraintlayout.widget.ConstraintSet
import android.animation.ValueAnimator
import android.os.Build
import android.view.View
import android.transition.TransitionManager
import android.view.MenuItem
import android.view.ViewAnimationUtils
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import com.android.kit.util.MathUtils
import kotlin.math.hypot


fun View.setDimensionRatio(ratio: String, animate: Boolean = false) {
    if (parent is ConstraintLayout) {
        val constraintLayout = parent as ConstraintLayout

        val cs1 = ConstraintSet()
        cs1.clone(constraintLayout)
        val cs2 = ConstraintSet()
        cs2.clone(constraintLayout)
        cs2.setDimensionRatio(id, ratio)

        if (animate) {
            TransitionManager.beginDelayedTransition(constraintLayout)
        }
        cs2.applyTo(constraintLayout)

//        val lp = layoutParams as ConstraintLayout.LayoutParams
//        layoutParams = lp.apply { dimensionRatio = ratio }
//        invalidate()
    }
}

fun View.setMarginConstraint(margin: Int, anchor: Int, animate: Boolean = false) {
    if (parent is ConstraintLayout) {
        val constraintLayout = parent as ConstraintLayout

        val cs1 = ConstraintSet()
        cs1.clone(constraintLayout)
        val cs2 = ConstraintSet()
        cs2.clone(constraintLayout)
        cs2.setMargin(id, anchor, margin)

        if (animate) {
            TransitionManager.beginDelayedTransition(constraintLayout)
        }
        cs2.applyTo(constraintLayout)
    }
}

fun View.translateX(newX: Float, duration: Long = 700) {
    val heightAnim = ValueAnimator.ofInt(x.toInt(), newX.toInt())
    heightAnim.addUpdateListener { valueAnimator ->
        val value = valueAnimator.animatedValue as Int
        x = value.toFloat()
    }
    heightAnim.duration = duration
    heightAnim.start()
}


fun View.translateY(newY: Float, duration: Long = 700) {
    val heightAnim = ValueAnimator.ofInt(y.toInt(), newY.toInt())
    heightAnim.addUpdateListener { valueAnimator ->
        val value = valueAnimator.animatedValue as Int
        y = value.toFloat()
    }
    heightAnim.duration = duration
    heightAnim.start()
}

fun View.updateWidth(w: Float, duration: Long = 700, animate: Boolean = false) {
    updateSize(w.toInt(), measuredHeight, duration, animate)
}

fun View.updateHeight(h: Float, duration: Long = 700, animate: Boolean = false) {
    updateSize(measuredWidth, h.toInt(), duration, animate)
}

fun View.updateSize(w: Float, h: Float, duration: Long = 700, animate: Boolean = false) {
    updateSize(w.toInt(), h.toInt(), duration, animate)
}

fun View.updateSize(w: Int, h: Int, duration: Long = 700, animate: Boolean = false) {
    if (animate) {
        val heightAnim = ValueAnimator.ofInt(measuredHeight, h)
        heightAnim.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val lp = layoutParams
            layoutParams = lp.apply {
                height = value
            }
        }

        val widthAnim = ValueAnimator.ofInt(measuredWidth, w)
        widthAnim.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val lp = layoutParams
            layoutParams = lp.apply {
                width = value
            }
        }

        heightAnim.duration = duration
        widthAnim.duration = duration

        heightAnim.start()
        widthAnim.start()
    } else {
        val params = layoutParams
        if (params != null) {
            layoutParams = params.apply {
                width = w
                height = h
            }
        }
    }
}

fun View.popupMenu(menuId: Int, callback: (MenuItem) -> Boolean) {
    val popup = PopupMenu(context, this)
    //Inflating the Popup using xml file
    popup.menuInflater.inflate(menuId, popup.menu)
    //registering popup with OnMenuItemClickListener
    popup.setOnMenuItemClickListener { callback(it) }
    popup.show()//showing popup menu
}

fun View.setMargin(left: Int, top: Int, right: Int, bottom: Int) {
    layoutParams = (layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
        setMargins(left, top, right, bottom)
    }
}

val View.sizeRatio: Pair<Float, Float>
    get() {
        return MathUtils.ratio(width, height)
    }

fun View.adjust(
    parent: ViewGroup,
    span: Int,
    orientation: Int,
    itemSpacing: Int,
    ratio: Float = 1f
) {
    val parentSize = if (orientation == LinearLayoutManager.HORIZONTAL) {
        parent.measuredHeight
    } else {
        parent.measuredWidth
    }
    val size = (parentSize / span) - ((itemSpacing * span) / 1.5)
    layoutParams.apply {
        width = size.toInt()
        height = (size * ratio).toInt()
    }
}

fun View.blockTouch(block: Boolean = true) {
    setOnTouchListener { _, _ -> block }
}

fun View.unReveal() {
    // Check if the runtime version is at least Lollipop
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        // get the center for the clipping circle
        val cx = width / 2
        val cy = height / 2

        // get the initial radius for the clipping circle
        val initialRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()

        // create the animation (the final radius is zero)
        val anim = ViewAnimationUtils.createCircularReveal(this, cx, cy, initialRadius, 0f)

        // make the view invisible when the animation is done
        anim.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                isVisible = false
            }
        })

        // start the animation
        anim.start()
    } else {
        // set the view to visible without a circular reveal animation below Lollipop
        isVisible = false
    }
}

fun View.reveal() {
// Check if the runtime version is at least Lollipop
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        // get the center for the clipping circle
        val cx = width / 2
        val cy = height / 2

        // get the final radius for the clipping circle
        val finalRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()

        // create the animator for this view (the start radius is zero)
        val anim = ViewAnimationUtils.createCircularReveal(this, cx, cy, 0f, finalRadius)
        // make the view visible and start the animation
        isVisible = true
        anim.start()
    } else {
        // set the view to invisible without a circular reveal animation below Lollipop
        isVisible = true
    }
}