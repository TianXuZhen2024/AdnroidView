package com.xuzhen.library_base.dialog.common

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.xuzhen.library_base.databinding.DialogLoadingBinding
import com.xuzhen.library_base.dialog.base.BaseDialogFragment

/**
 *
 * 加载loading dialog
 * @author TXZ
 * @version 1.0
 * created by 2024/5/22 15:50
 */
class LoadingDialogFragment : BaseDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = DialogLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLocation(Gravity.CENTER)
    }

    override fun setLocation(gravity: Int) {
        //默认dialog宽度为屏幕的0.8 居中显示
        dialog?.window?.apply {
            val layoutParams = attributes
            layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            layoutParams.gravity = Gravity.CENTER
            attributes = layoutParams
        }
    }
}