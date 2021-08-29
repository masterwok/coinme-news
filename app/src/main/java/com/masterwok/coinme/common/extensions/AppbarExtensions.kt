package com.masterwok.coinme.common.extensions

import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout

/**
 * Disable dragging directly on an [AppBarLayout]. This can be used to prevent a
 * [CollapsingToolbarLayout] from collapsing when there isn't enough scrollable content.
 */
fun AppBarLayout.disableDrag() {
    val layoutParams = (layoutParams as CoordinatorLayout.LayoutParams).apply {
        behavior = behavior ?: AppBarLayout.Behavior()
    }

    (layoutParams.behavior as AppBarLayout.Behavior).apply {
        setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
            override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                return false
            }
        })
    }
}