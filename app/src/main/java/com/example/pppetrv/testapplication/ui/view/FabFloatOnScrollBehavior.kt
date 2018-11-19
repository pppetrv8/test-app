package com.example.pppetrv.testapplication.ui.view

import android.content.Context
import android.support.v4.view.ViewCompat
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

class FabFloatOnScrollBehavior(context: Context, attrs: AttributeSet) : FloatingActionButton.Behavior(context, attrs) {

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton,
                                target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)

        //child -> Floating Action Button
//        if (dyConsumed > 0) {
//            val layoutParams = child.layoutParams as CoordinatorLayout.LayoutParams
//            val fab_bottomMargin = layoutParams.bottomMargin
//            child.animate().translationY((child.height + fab_bottomMargin).toFloat()).setInterpolator(LinearInterpolator()).start()
//        } else if (dyConsumed < 0) {
//            child.animate().translationY(0f).setInterpolator(LinearInterpolator()).start()
//        }
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton,
                                     directTargetChild: View, target: View, nestedScrollAxes: Int, type: Int): Boolean {
        val layoutParams = child.layoutParams as CoordinatorLayout.LayoutParams
        val fab_bottomMargin = layoutParams.bottomMargin
        child.animate().translationY((child.height + fab_bottomMargin).toFloat()).setInterpolator(LinearInterpolator()).start()

        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, target: View, type: Int) {
        super.onStopNestedScroll(coordinatorLayout, child, target, type)
        child.animate().translationY(0f).setInterpolator(LinearInterpolator()).start()
    }
}