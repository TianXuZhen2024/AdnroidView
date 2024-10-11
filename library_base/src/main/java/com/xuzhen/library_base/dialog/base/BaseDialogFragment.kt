package com.xuzhen.library_base.dialog.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.xuzhen.library_base.R
import com.xuzhen.library_base.utils.ScreenUtils

/**
 *
 *  基础Dialog的弹框
 * @author TXZ
 * @version 1.0
 * created by 2024/4/26 9:56
 */
abstract class BaseDialogFragment : DialogFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.base_center_dialog)
    }

    /**
     * 设置弹框的位置
     * @param gravity
     */
    protected open fun setLocation(gravity: Int) {
        when (gravity) {
            Gravity.TOP -> {
                dialog?.window?.apply {
                    //默认dialog宽度为屏幕的0.8 居中显示
                    val layoutParams = attributes
                    layoutParams.width = (ScreenUtils.getScreenWidth() * 0.8f).toInt()
                    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
                    layoutParams.gravity = Gravity.TOP
                    attributes = layoutParams
                }
            }
            Gravity.CENTER->{
                //默认dialog宽度为屏幕的0.8 居中显示
                dialog?.window?.apply {
                    val layoutParams = attributes
                    layoutParams.width = (ScreenUtils.getScreenWidth() * 0.8f).toInt()
                    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
                    layoutParams.gravity = Gravity.CENTER
                    attributes = layoutParams
                }
            }
            Gravity.BOTTOM->{
                dialog?.window?.apply {
                    val layoutParams = attributes
                    layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
                    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
                    layoutParams.gravity = Gravity.BOTTOM
                    attributes = layoutParams
                    decorView.setPadding(0, 0, 0, 0)
                    setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                }
            }
        }
    }

    /**
     * 设置弹框是否可以取消
     * @param isCancel
     */
    fun setDialogCancel(isCancel: Boolean) {
        isCancelable = isCancel
        dialog?.setCanceledOnTouchOutside(isCancel)
    }


    fun show(fm: FragmentManager) {
        show(fm, javaClass.simpleName)
    }

    override fun dismiss() {
        if (isAdded && dialog?.isShowing == true) {
            super.dismiss()
        }
    }
}