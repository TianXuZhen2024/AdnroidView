package com.xuzhen.library_base.dialog.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xuzhen.library_base.databinding.DialogTwoBtnBinding
import com.xuzhen.library_base.dialog.base.BaseDialogFragment

/**
 *
 * 两个按钮的常用弹框
 * @author TXZ
 * @version 1.0
 * created by 2024/6/4 11:52
 */
class TwoBtnDialogFragment() : BaseDialogFragment() {
    private var _builderConfig = Builder()
    private lateinit var _binding: DialogTwoBtnBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogTwoBtnBinding.inflate(inflater, container, false)
        _binding.apply {
            tvTitle.text = _builderConfig.tvTitle
            tvContent.text = _builderConfig.tvContent
            tvCancel.text = _builderConfig.tvCancel
            tvSure.text = _builderConfig.tvSure
            tvCancel.setOnClickListener {
                _builderConfig.onCancelClick()
                dismiss()
            }
            tvSure.setOnClickListener {
                _builderConfig.onSureClick()
                dismiss()
            }
        }
        return _binding.root
    }


    inner class Builder() {

        var tvTitle: String = ""

        var tvContent: String = ""

        var tvCancel: String = ""

        var tvSure: String = ""

        var onCancelClick: () -> Unit = {

        }

        //不能直接在Build里面设置dismiss，因为fragmentManager 还没初始化
        var onSureClick: () -> Unit = {

        }

        fun create(): TwoBtnDialogFragment {
            return this@TwoBtnDialogFragment.apply {
                _builderConfig = this@Builder
            }
        }
    }
}