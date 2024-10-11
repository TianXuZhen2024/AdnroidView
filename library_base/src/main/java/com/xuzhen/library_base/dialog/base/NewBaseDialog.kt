package com.xuzhen.library_base.dialog.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.xuzhen.library_base.R

/**
 *
 * 基于Dialog的基类
 * @see BaseDialogFragment 优先使用DialogFragment
 * @author TXZ
 * @version 1.0
 * created by 2024/5/10 15:39
 */
abstract class NewBaseDialog : Dialog, LifecycleObserver {
    /**
     * 构造方法全部基于super(context, themeResId) ，设置基础style
     */
    constructor(context: Context) : this(context, R.style.base_dialog)

    constructor(context: Context, themeResId: Int) : super(context, themeResId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (context is LifecycleOwner) (context as LifecycleOwner).lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        if (isShowing) dismiss()
    }
}