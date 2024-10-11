package com.xuzhen.library_base.dialog.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.xuzhen.library_base.databinding.DialogOneBtnBinding
import com.xuzhen.library_base.dialog.base.BaseDialogFragment


/**
 *
 * 只有一个按钮的提示dialog，集成BaseCenterDialog的圆角 r=8dp
 * @author TXZ
 * @version 1.0
 * created by 2024/5/13 15:37
 */
class OneBtnInfoDialogFragment : BaseDialogFragment() {
    private var _builderConfig = Builder()

    //不同项目的oneBtn应该是不一样的，根据设计稿修改一些view的大小跟颜色即可
    private lateinit var binding: DialogOneBtnBinding

    private lateinit var onClickListener: OnClickListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DialogOneBtnBinding.inflate(inflater, container, false)
        if (_builderConfig.content.isEmpty()) {
            throw Exception("OneBtnDialogFragment 构建错误，缺少content主体内容")
        }
        //绑定参数
        binding.apply {
            tvTitle.text = _builderConfig.title
            tvContent.text = _builderConfig.content
            btn.text = _builderConfig.btnText
            //如果build 跟 dialog同时实现，则优先选择dialog
            if (::onClickListener.isInitialized) {
                btn.setOnClickListener(onClickListener)
            } else {
                btn.setOnClickListener {
                    _builderConfig.onBtnClick()
                    dismiss()
                }
            }
        }
        return binding.root
    }

    fun setContent(content: String) {
        _builderConfig.content = content
    }


    fun setBtnOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }


    inner class Builder() {
        var title = "提示"
        var content: String = ""
        var btnText: String = "确认"
        //不能直接在Build里面设置dismiss，因为fragmentManager 还没初始化
        var onBtnClick: () -> Unit = {

        }

        fun create(): OneBtnInfoDialogFragment {
            return this@OneBtnInfoDialogFragment.apply {
                _builderConfig = this@Builder
            }
        }
    }


}