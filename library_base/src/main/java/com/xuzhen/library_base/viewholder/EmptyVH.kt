package com.xuzhen.library_base.viewholder

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView

/**
 * @author: TXZ
 * @version: 1.0
 * @date: created by 2024/9/28 19:33
 */
class EmptyVH(
    parent: ViewGroup,
    stateView: View?,
    private val stateLayout: FrameLayout = FrameLayout(parent.context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        setStateView(this, stateView)
    }
) : RecyclerView.ViewHolder(stateLayout) {


    fun changeStateView(stateView: View?) {
        setStateView(stateLayout, stateView)
    }

    companion object {
        private fun setStateView(rootView: ViewGroup, stateView: View?) {
            if (stateView == null) {
                rootView.removeAllViews()
                return
            }

            if (rootView.childCount == 1) {
                val old = rootView.getChildAt(0)
                if (old == stateView) {
                    // 如果是同一个view，不进行操作
                    return
                }
            }

            stateView.parent.run {
                if (this is ViewGroup) {
                    this.removeView(stateView)
                }
            }

            if (stateView.layoutParams == null) {
                stateView.layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = Gravity.CENTER
                }
            }

            rootView.removeAllViews()
            rootView.addView(stateView)
        }
    }
}
